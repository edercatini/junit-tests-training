package br.ec.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.ec.exceptions.EmptyUserException;

public class EmptyUserServiceTest {

	@Rule
	public ExpectedException exception;

	private EmptyUserService service;

	public EmptyUserServiceTest() {
		this.exception = ExpectedException.none();
		this.service = new EmptyUserService();
	}

	@Test
	public void mustThrowExceptionIfUserIsEmpty() throws EmptyUserException {
		exception.expect(EmptyUserException.class);
		exception.expectMessage("Invalid User");

		service.check(null);
	}
}