package nl.knokko.util.hashing.encryptor;

import nl.knokko.util.bits.BitHelper;
import nl.knokko.util.random.Random;

public class SimpleEncryptor implements Encryptor {

	private static final int MIN_LENGTH = 40;

	private final Random random;

	public SimpleEncryptor(Random random) {
		this.random = random;
	}
	
	private int[] randomPermutation(int length) {
		int[] array = new int[length];
		for (int index = 0; index < array.length; index++) {
			array[index] = index;
		}
		
		for (int index = 0; index < array.length; index++) {
			int next = random.fastNextInt(array.length - index) + index;
			int old = array[next];
			array[next] = array[index];
			array[index] = old;
		}
		
		return array;
	}
	
	private static final int HEADER_LENGTH = 5 * 4;

	@Override
	public byte[] encrypt(byte[] payload) {
		// The payload itself, plus the payload length, the checksum and the
		// checkproduct before and after encryption
		int importantSize = payload.length + HEADER_LENGTH;
		int size = Math.max(importantSize, MIN_LENGTH);
		byte[] data = new byte[size];

		int payloadSum = 0;
		int payloadProduct = 1;
		for (byte pay : payload) {
			payloadSum += pay;
			// Only multiply by numbers greater than 0 (overflow is expected)
			payloadProduct *= pay + 129;
		}

		// Determine how to swap everything
		// The header contains the length of the payload and several checks
		int headerIndex = random.fastNextInt(size - HEADER_LENGTH + 1);
		int payloadLengthIndex = headerIndex;
		int payloadSumIndex = headerIndex + 4;
		int payloadProductIndex = headerIndex + 8;
		int dataSumIndex = headerIndex + 12;
		int dataProductIndex = headerIndex + 16;

		putInt(data, payload.length, payloadLengthIndex);
		putInt(data, payloadSum, payloadSumIndex);
		putInt(data, payloadProduct, payloadProductIndex);
		// the dataSum and dataProduct are unknown at this stage, so store them later

		// Increase every byte by a random amount
		for (int index = 0; index < size; index++) {
			data[index] += random.nextByte();
		}

		// Assign every byte in the payload a place in the data
		int[] permutation = randomPermutation(payload.length);
		for (int index = 0; index < permutation.length; index++) {
			if (permutation[index] >= headerIndex) {
				data[permutation[index] + HEADER_LENGTH] += payload[index];
			} else {
				data[permutation[index]] += payload[index];
			}
		}

		// The dataSum and dataProduct do not contribute to the stored dataSum and
		// dataProduct values
		// because that is going to be very annoying.
		int dataSum = 0;
		int dataProduct = 1;
		for (byte d : data) {
			dataSum += d;
			dataProduct *= d + 129;
		}

		increaseInt(data, dataSum, dataSumIndex);
		increaseInt(data, dataProduct, dataProductIndex);
		return data;
	}

	private void putInt(byte[] data, int value, int index) {
		data[index] = BitHelper.int0(value);
		data[++index] = BitHelper.int1(value);
		data[++index] = BitHelper.int2(value);
		data[++index] = BitHelper.int3(value);
	}

	private void increaseInt(byte[] data, int value, int index) {
		data[index] += BitHelper.int0(value);
		data[++index] += BitHelper.int1(value);
		data[++index] += BitHelper.int2(value);
		data[++index] += BitHelper.int3(value);
	}

	private void decreaseInt(byte[] data, int value, int index) {
		data[index] -= BitHelper.int0(value);
		data[++index] -= BitHelper.int1(value);
		data[++index] -= BitHelper.int2(value);
		data[++index] -= BitHelper.int3(value);
	}

	private int getInt(byte[] data, int index) {
		return BitHelper.makeInt(data[index], data[++index], data[++index], data[++index]);
	}

	@Override
	public byte[] decrypt(byte[] data) {
		int size = data.length;
		int headerIndex = random.fastNextInt(size - 19);
		int payloadLengthIndex = headerIndex;
		int payloadSumIndex = headerIndex + 4;
		int payloadProductIndex = headerIndex + 8;
		int dataSumIndex = headerIndex + 12;
		int dataProductIndex = headerIndex + 16;
		
		// The adders were obtained first by the random during encryption, to we need to store them
		byte[] adders = new byte[size];
		for (int index = 0; index < size; index++) {
			adders[index] = random.nextByte();
		}

		// Don't overwrite data because we need to check the product and sum of the data
		byte[] decryptedData = new byte[size];
		for (int index = 0; index < size; index++) {
			decryptedData[index] = data[index];
			decryptedData[index] -= adders[index];
		}

		int dataSum = getInt(decryptedData, dataSumIndex);
		int dataProduct = getInt(decryptedData, dataProductIndex);
		int payloadLength = getInt(decryptedData, payloadLengthIndex);
		int payloadSum = getInt(decryptedData, payloadSumIndex);
		int payloadProduct = getInt(decryptedData, payloadProductIndex);

		// The dataSum and dataProduct itself do not contribute to the stored values
		decreaseInt(data, dataSum, dataSumIndex);
		decreaseInt(data, dataProduct, dataProductIndex);

		// Compare received data sum and data product with the actual sum and product
		int checkDataSum = 0;
		int checkDataProduct = 1;
		for (byte d : data) {
			checkDataSum += d;
			checkDataProduct *= d + 129;
		}

		if (dataSum != checkDataSum) {
			System.out
					.println("the dataSum (" + dataSum + ") and the checkDataSum (" + checkDataSum + ") do not match.");
			return null;
		}

		if (dataProduct != checkDataProduct) {
			System.out.println("the dataProduct (" + dataProduct + ") and the checkDataProduct is (" + checkDataProduct
					+ ") do not match.");
			return null;
		}

		byte[] payload = new byte[payloadLength];
		
		// Revert the swap operation
		int[] permutation = randomPermutation(payloadLength);
		for (int index = 0; index < payloadLength; index++) {
			if (permutation[index] >= headerIndex) {
				payload[index] = decryptedData[permutation[index] + HEADER_LENGTH];
			} else {
				payload[index] = decryptedData[permutation[index]];
			}
		}
		
		// Check if the read payload sum and payload product match the actual sum and product
		int checkPayloadSum = 0;
		int checkPayloadProduct = 1;
		for (byte p : payload) {
			checkPayloadSum += p;
			checkPayloadProduct *= p + 129;
		}

		if (payloadSum != checkPayloadSum) {
			System.out.println(
					"payloadSum (" + payloadSum + ") and checkPayloadSum (" + checkPayloadSum + ") do not match.");
			return null;
		}

		if (payloadProduct != checkPayloadProduct) {
			System.out.println("payloadProduct (" + payloadProduct + ") and checkPayloadProduct (" + checkPayloadProduct
					+ ") do not match.");
			return null;
		}

		return payload;
	}
}