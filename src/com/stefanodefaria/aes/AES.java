package com.stefanodefaria.aes;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	/**
	 * Encrypts input data using a passphrase.
	 * 
	 * @param passphrase		Password to be used to encrypt data
	 * @param rawDataStream		Stream from which raw data will be read
	 * @param encryptedStream	Stream to which encrypted data will be written
	 * @throws IOException
	 * @throws AESException
	 */
	public static void encrypt(String passphrase, InputStream rawDataStream, OutputStream encryptedStream)
			throws IOException, AESException {

		try {
			final SecretKey key = generateKey(passphrase);
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			CipherOutputStream cipherStream = new CipherOutputStream(encryptedStream, cipher);

			// Reads 1024 bytes at a time from inputStream, encrypts them and writes them to outputStream
			byte[] buffer = new byte[1024];
			int length;
			while ((length = rawDataStream.read(buffer)) > 0) {
				cipherStream.write(buffer, 0, length);
			}

			cipherStream.close();

		} catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException e) {
			throw new AESException(e.getMessage());
		}
	}

	/**
	 * @param passphrase		Password to be used to decrypt data
	 * @param encryptedStream	Stream from which encrypted data will be read
	 * @param decryptedStream  	Stream to which decrypted data will be written
	 * @throws IOException
	 * @throws AESException
	 */
	public static void decrypt(String passphrase, InputStream encryptedStream, OutputStream decryptedStream)
			throws IOException, AESException {

		CipherOutputStream cipherStream = null;

		try {
			final SecretKey key = generateKey(passphrase);
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			cipherStream = new CipherOutputStream(decryptedStream, cipher);

			// Reads 1024 bytes at a time from encryptedStream, decrypts them and writes them to decryptedStream
			byte[] buffer = new byte[1024];
			int length;
			while ((length = encryptedStream.read(buffer)) > 0) {
				cipherStream.write(buffer, 0, length);
			}
			cipherStream.close();

		} catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException e) {
			throw new AESException(e.getMessage());
		}
	}

	private static SecretKey generateKey(String passphrase) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] key = (passphrase).getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		key = Arrays.copyOf(sha.digest(key), 16); // use only first 128 bit
		return new SecretKeySpec(key, "AES");
	}
}
