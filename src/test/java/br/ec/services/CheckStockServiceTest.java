package br.ec.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ec.domain.Movie;
import br.ec.exceptions.OutOfStockException;

public class CheckStockServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	private List<Movie> movies;

	private CheckStockService service;

	public CheckStockServiceTest() {
		this.service = new CheckStockService();
		this.movies = new ArrayList<Movie>();
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test(expected = OutOfStockException.class)
	public void mustThrowOutOfStockExceptionIfOneMovieHasInsufficientStockForRent() throws OutOfStockException {
		this.movies.addAll(Arrays.asList(new Movie("Filme 1", 0, 5d), new Movie("Filme 2", 1, 5d)));
		service.inStock(this.movies);
	}

	@Test
	public void mustAllocateIfStockIsEnough() throws OutOfStockException {
		this.movies.addAll(Arrays.asList(new Movie("Filme 1", 2, 5d), new Movie("Filme 2", 1, 5d)));
		error.checkThat(service.inStock(this.movies), is(equalTo(true)));
	}
}