package br.ec.services;

import static br.ec.utils.DateUtils.incrementCurrentDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;
import br.ec.exceptions.OutOfStockException;

public class LocationServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	private User user = new User("Usuário 1");
	
	private List<Movie> movies = new ArrayList<Movie>();
	
	private LocationService locationService;

	private Format formatter;

	@Before
	public void setUp() {
		this.locationService = new LocationService(new CheckStockService());
		this.formatter = new SimpleDateFormat();
	}

	@After
	public void tearDown() {
	}
	
	private Location allocateMovie(User user, List<Movie> movies) throws OutOfStockException {
		return locationService.allocateMovie(this.user, movies);
	}

	@Test
	public void mustBillASingleMoviePrice() throws OutOfStockException {
		this.movies.add(new Movie("Filme 1", 1, 5d));
		error.checkThat(this.allocateMovie(this.user, this.movies).getBillingValue(), is(equalTo(5d)));
	}

	@Test
	public void locationDateMustBeCurrentDate() throws OutOfStockException {
		this.movies.add(new Movie("Filme 1", 1, 5d));
		error.checkThat(this.formatter.format(this.allocateMovie(this.user, this.movies).getLocationDate()), is(equalTo(formatter.format(new Date()))));
	}

	@Test
	public void returningDateMustBeNextDay() throws OutOfStockException {
		this.movies.add(new Movie("Filme 1", 1, 5d));
		error.checkThat(this.formatter.format(this.allocateMovie(user, this.movies).getReturningDate()), is(equalTo(formatter.format(incrementCurrentDate(1)))));
	}

	@Test(expected = OutOfStockException.class)
	public void testMustNotAllocateIfStockIsInsufficientForAtLeastOneMovie() throws OutOfStockException {
		this.movies.addAll(Arrays.asList(new Movie("Filme 1", 0, 5d), new Movie("Filme 2", 1, 5d)));
		this.allocateMovie(this.user, this.movies);
	}
}