package br.ec.services;

import java.util.List;

import br.ec.domain.Movie;
import br.ec.exceptions.OutOfStockException;

public class CheckStockService {

	public void inStock(List<Movie> movies) throws OutOfStockException {

		for (Movie movie : movies) {
			if (movie.getStock() == 0) {
				throw new OutOfStockException("Insufficient stock");
			}
		}
	}
}