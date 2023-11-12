package com.encora.ibk.plancorazon.exception;

public class PersonNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(String message, String codigoUnico) {
		super(String.format("%s %d", message, codigoUnico));
	}

	public PersonNotFoundException(String message) {
		super(message);
	}

	public PersonNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonNotFoundException(String message, String codigoUnico, Throwable cause) {
		super(String.format("%s %d", message, codigoUnico), cause);
	}
}