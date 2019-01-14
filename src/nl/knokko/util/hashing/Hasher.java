/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.util.hashing;

import nl.knokko.util.hashing.result.HashResult;
import nl.knokko.util.random.PseudoRandom;
import nl.knokko.util.random.Random;
import nl.knokko.util.random.RandomArray;

public class Hasher {

	public static HashResult tempHash(HashResult clientHash, int temp1, int temp2, int temp3, int temp4) {
		int[] client = clientHash.get();
		Random random = new RandomArray(
				new PseudoRandom(client[0], client[14], client[1], client[12], temp1, temp4, client[2], client[17]),
				new PseudoRandom(client[3], client[19], client[4], client[10], client[5], client[15], temp2, temp3),
				new PseudoRandom(client[6], client[11], client[7], client[13], client[8], client[16], client[9],
						client[18]));
		return new HashResult(client[0] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[7] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[4] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[2] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[8] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[3] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[1] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[6] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[5] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[9] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[17] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[12] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[15] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[19] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[10] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[13] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[16] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[18] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[11] + random.nextInt() + random.nextInt() + random.nextInt(),
				client[14] + random.nextInt() + random.nextInt() + random.nextInt());
	}

	public static HashResult encrypt(HashResult clientHash, int[] encryptor) {
		int[] client = clientHash.get();
		int[] result = new int[20];
		for (int index = 0; index < 20; index++)
			result[index] = client[index] + encryptor[index];
		return new HashResult(result);
	}

	public static HashResult decrypt(HashResult encrypted, int[] encryptor) {
		int[] crypted = encrypted.get();
		int[] result = new int[20];
		for (int index = 0; index < 20; index++)
			result[index] = crypted[index] - encryptor[index];
		return new HashResult(result);
	}
}