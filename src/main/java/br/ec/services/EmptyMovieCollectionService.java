package br.ec.services;

import java.util.List;

import br.ec.domain.Movie;
import br.ec.exceptions.EmptyMovieCollectionException;

public class EmptyMovieCollectionService {

	public void check(List<Movie> movies) throws EmptyMovieCollectionException {
		if (movies.size() == 0) {
			throw new EmptyMovieCollectionException("Invalid Movie Collection");
		}
	}
}