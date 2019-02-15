package br.ec.services;

import br.ec.domain.User;
import br.ec.exceptions.EmptyUserException;

public class EmptyUserService {

	public void check(User user) throws EmptyUserException {
		if (user == null) {
			throw new EmptyUserException("Invalid User");
		}
	}
}