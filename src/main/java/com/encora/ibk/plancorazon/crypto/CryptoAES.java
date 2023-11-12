package com.encora.ibk.plancorazon.crypto;

import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptoAES {

	// Método principal para demostrar la encriptación y desencriptación
	public static void main(String[] args) {
		try {
			String originalData = UUID.randomUUID().toString();
			SecretKey secretKey = generateKey();

			String encryptedData = encrypt(originalData, secretKey);

			System.out.println("Original: " + originalData);
			System.out.println("Encriptado: " + encryptedData);
			System.out.println("Desencriptado: " + decrypt(encryptedData, secretKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Generar una clave secreta
	public static SecretKey generateKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256); // Puedes elegir 128, 192 o 256 bits
		return keyGenerator.generateKey();
	}

	// Encriptar datos
	public static String encrypt(String data, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	// Desencriptar datos
	public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
		return new String(decryptedBytes);
	}
}
