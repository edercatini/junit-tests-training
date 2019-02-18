package br.ec.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ec.builders.MovieDataBuilder;
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
		this.movies = new ArrayList<Movie>(Arrays.asList(
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Filme 1").withNumberInStock(2).build(),
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Filme 2").withNumberInStock(0).build()
		));

		this.exception.expect(OutOfStockException.class);
		this.exception.expectMessage("Insufficient stock");

		service.inStock(this.movies);
	}
}