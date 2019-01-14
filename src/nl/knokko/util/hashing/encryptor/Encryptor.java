package nl.knokko.util.hashing.encryptor;

public interface Encryptor {
	
	byte[] encrypt(byte[] payload);
	
	byte[] decrypt(byte[] data);
}