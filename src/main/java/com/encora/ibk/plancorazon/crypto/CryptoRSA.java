package com.encora.ibk.plancorazon.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;
import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

@Component
public class CryptoRSA {

	private PublicKey publicKey;
	private PrivateKey privateKey;

	public static void main(String[] args) {
		try {
			CryptoRSA rsaEncryption = new CryptoRSA();

			String originalMessage = UUID.randomUUID().toString();
			byte[] encryptedMessage = rsaEncryption.encrypt(originalMessage);

			System.out.println("Original: " + originalMessage);
			System.out.println("Encriptado: " + new String(encryptedMessage));
			System.out.println("Desencriptado: " + rsaEncryption.decrypt(encryptedMessage));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CryptoRSA() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(3072);// 2048 y 3072
		KeyPair pair = keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public byte[] encrypt(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		return cipher.doFinal(message.getBytes());
	}

	public String decrypt(byte[] encryptedMessage) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		return new String(cipher.doFinal(encryptedMessage));
	}
}
