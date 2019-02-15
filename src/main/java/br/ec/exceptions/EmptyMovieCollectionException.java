package br.ec.exceptions;

public class EmptyMovieCollectionException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyMovieCollectionException(String message) {
		super(message, new Throwable());
	}
}