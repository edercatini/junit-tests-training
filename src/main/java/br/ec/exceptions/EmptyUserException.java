package br.ec.exceptions;

public class EmptyUserException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyUserException(String message) {
		super(message, new Throwable());
	}
}