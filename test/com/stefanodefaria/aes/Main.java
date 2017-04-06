package com.stefanodefaria.aes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

	public static void main(String args[]) {
		final String basePath = "test/com/stefanodefaria/aes/",
				inputFilePath = basePath + "input.txt",
				encryptedFilePath = basePath + "encrypted.aes",
				decryptedFilePath = basePath + "decrypted.txt",
				passphrase = "Password123", salt = "4ddS0m3S@lt!";

		FileInputStream rawFileInput;
		FileOutputStream encryptedFileOutput;
		FileInputStream encryptedFileInput;
		FileOutputStream decryptedFileInput;

		try {

			System.out.print("Encrypting file...");
			rawFileInput = new FileInputStream(inputFilePath);
			encryptedFileOutput = new FileOutputStream(encryptedFilePath);
			AES.encrypt(passphrase + salt, rawFileInput, encryptedFileOutput);
			System.out.println(" Success!\n");

			System.out.print("Decrypting file...");
			encryptedFileInput = new FileInputStream(encryptedFilePath);
			decryptedFileInput = new FileOutputStream(decryptedFilePath);
			AES.decrypt(passphrase + salt, encryptedFileInput, decryptedFileInput);
			System.out.println(" Success!\n");

		} catch (IOException | AESException e) {
			System.out.println("\nERROR: ");
			e.printStackTrace();
		}
	}
}
