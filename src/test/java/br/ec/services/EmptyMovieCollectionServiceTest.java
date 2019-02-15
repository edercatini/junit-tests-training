package br.ec.services;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import br.ec.domain.Movie;
import br.ec.exceptions.EmptyMovieCollectionException;

public class EmptyMovieCollectionServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private EmptyMovieCollectionService service;

	public void mustNotRentIfMoviesCollectionIsEmpty() throws EmptyMovieCollectionException {
		this.exception.expect(EmptyMovieCollectionException.class);
		this.exception.expectMessage("Invalid Movie Collection");

		service.check(new ArrayList<Movie>());
	}
}