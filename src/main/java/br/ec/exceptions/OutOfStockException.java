package br.ec.exceptions;

public class OutOfStockException extends Exception {
	private static final long serialVersionUID = 1L;

	public OutOfStockException(String message, Throwable throwable) {
		super(message, throwable);
	}
}