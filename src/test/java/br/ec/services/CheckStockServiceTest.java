package br.ec.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ec.domain.Movie;
import br.ec.exceptions.OutOfStockException;

public class CheckStockServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private List<Movie> movies;

	private CheckStockService service;

	public CheckStockServiceTest() {
		this.service = new CheckStockService();
		this.movies = new ArrayList<Movie>();
	}

	@Test
	public void mustThrowOutOfStockExceptionIfOneMovieHasInsufficientStockForRent() throws OutOfStockException {
		this.movies.addAll(Arrays.asList(new Movie("Filme 1", 0, 5d), new Movie("Filme 2", 1, 5d)));
		this.exception.expect(OutOfStockException.class);
		this.exception.expectMessage("Insufficient stock");

		service.inStock(this.movies);
	}
}