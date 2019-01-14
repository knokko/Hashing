package nl.knokko.test;

import java.util.Arrays;

import nl.knokko.util.hashing.encryptor.Encryptor;
import nl.knokko.util.hashing.encryptor.SimpleEncryptor;
import nl.knokko.util.random.PseudoRandom;
import nl.knokko.util.random.Random;

public class Tester {

	public static void main(String[] args) {
		Random random = new PseudoRandom();
		random.nextInt();
		random.nextBoolean();
		Random clone1 = random.clone();
		Random clone2 = random.clone();
		Encryptor encryptor1 = new SimpleEncryptor(clone1);
		Encryptor encryptor2 = new SimpleEncryptor(clone2);
		for (int i = 0; i < 1000; i++) {
			byte[] original = random.nextBytes(1000);
			byte[] encrypted = encryptor1.encrypt(original);
			byte[] decrypted = encryptor2.decrypt(encrypted);
			if (!Arrays.equals(original, decrypted)) {
				System.out.println("did not equal!");
				
				System.out.println("original is " + Arrays.toString(original));
				System.out.println("encrypted is " + Arrays.toString(encrypted));
				System.out.println("decrypted is " + Arrays.toString(decrypted));
			}
		}
	}
}