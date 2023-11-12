package com.encora.ibk.plancorazon.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CryptoHomomorfico {

	private BigInteger p, q, n;
	private BigInteger phi, e, d;
	private SecureRandom random;

	public static void main(String[] args) {
		CryptoHomomorfico he = new CryptoHomomorfico();
		String mensaje = UUID.randomUUID().toString();
		System.out.println("Original: " + mensaje);

		// Encriptación
		List<BigInteger> encriptado = he.encryptString(mensaje);
		System.out.println("Mensaje encriptado: " + encriptado);

		// Desencriptación
		String desencriptado = he.decryptString(encriptado);
		System.out.println("Mensaje desencriptado: " + desencriptado);
	}

	public CryptoHomomorfico() {
		random = new SecureRandom();
		p = BigInteger.probablePrime(1024, random);
		q = BigInteger.probablePrime(1024, random);
		n = p.multiply(q);
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("65537"); // valor común para e
		d = e.modInverse(phi);
	}

	public BigInteger encrypt(BigInteger message) {
		return message.modPow(e, n);
	}

	public BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(d, n);
	}

	public List<BigInteger> encryptString(String message) {
		List<BigInteger> encryptedMessage = new ArrayList<>();
		for (char character : message.toCharArray()) {
			BigInteger encryptedChar = encrypt(BigInteger.valueOf((int) character));
			encryptedMessage.add(encryptedChar);
		}
		return encryptedMessage;
	}

	public String decryptString(List<BigInteger> encryptedMessage) {
		StringBuilder decryptedMessage = new StringBuilder();
		for (BigInteger encryptedChar : encryptedMessage) {
			char character = (char) decrypt(encryptedChar).intValue();
			decryptedMessage.append(character);
		}
		return decryptedMessage.toString();
	}
}
