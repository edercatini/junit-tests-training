package br.ec.services;

import java.util.List;

import br.ec.domain.Movie;
import br.ec.exceptions.OutOfStockException;

public class CheckStockService {

	public Boolean inStock(List<Movie> movies) throws OutOfStockException {

		for (Movie movie : movies) {
			if (movie.getStock() == 0) {
				throw new OutOfStockException("Insuficient stock for " + movie.getName(),
						new Throwable("Insuficient stock for " + movie.getName()));
			}
		}

		return true;
	}
}