package br.ec.services;

import static br.ec.utils.DateUtils.incrementCurrentDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;
import br.ec.exceptions.EmptyMovieCollectionException;
import br.ec.exceptions.EmptyUserException;
import br.ec.exceptions.OutOfStockException;

public class LocationServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Rule
	public ErrorCollector error = new ErrorCollector();

	private User user = new User("Usuário 1");

	private LocationService locationService = new LocationService(new CheckStockService(), new EmptyUserService(),
			new EmptyMovieCollectionService());

	private Format formatter = new SimpleDateFormat();

	private Location rentMovies(User user, List<Movie> movies)
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		return locationService.rentMovies(user, movies);
	}

	@Test
	public void mustBillASingleMoviePrice()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(new Movie("Filme 1", 1, 5d)));
		error.checkThat(this.rentMovies(this.user, movies).getBillingValue(), is(equalTo(5d)));
	}

	@Test
	public void locationDateMustBeCurrentDate()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(new Movie("Filme 1", 1, 5d)));
		error.checkThat(this.formatter.format(this.rentMovies(this.user, movies).getLocationDate()),
				is(equalTo(formatter.format(new Date()))));
	}

	@Test
	public void returningDateMustBeNextDay()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(new Movie("Filme 1", 1, 5d)));

		error.checkThat(this.formatter.format(this.rentMovies(user, movies).getReturningDate()),
				is(equalTo(formatter.format(incrementCurrentDate(1)))));
	}

	@Test
	public void mustNotRentIfStockIsInsufficientForAtLeastOneMovie()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(
				Arrays.asList(new Movie("Filme 1", 0, 5d), new Movie("Filme 2", 1, 5d)));

		this.exception.expect(OutOfStockException.class);
		this.exception.expectMessage("Insufficient stock");

		this.rentMovies(this.user, movies);
	}

	@Test
	public void mustNotRentIfUserIsInvalid()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(
				Arrays.asList(new Movie("Filme 1", 1, 5d), new Movie("Filme 2", 1, 5d)));

		this.exception.expect(EmptyUserException.class);
		this.exception.expectMessage("Invalid User");

		this.rentMovies(null, movies);
	}

	@Test
	public void mustNotRentIfMovieCollectionIsEmpty()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		this.exception.expect(EmptyMovieCollectionException.class);
		this.exception.expectMessage("Invalid Movie Collection");
		this.rentMovies(this.user, new ArrayList<Movie>());
	}

	@Test
	public void mustGive25PercentDiscountOnThirdRentMovie()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(
				Arrays.asList(new Movie("Filme 1", 1, 4d), new Movie("Filme 2", 1, 4d), new Movie("Filme 3", 1, 4d)));

		Location location = this.rentMovies(this.user, movies);

		assertEquals(11d, location.getBillingValue(), 0.001);
	}

	@Test
	public void mustGive50PercentDiscountOnFourthRentMovie()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(new Movie("Filme 1", 1, 4d),
				new Movie("Filme 2", 1, 4d), new Movie("Filme 3", 1, 4d), new Movie("Filme 4", 1, 4d)));

		Location location = this.rentMovies(this.user, movies);

		assertEquals(13d, location.getBillingValue(), 0.001);
	}

	@Test
	public void mustGive75PercentDiscountOnFifthRentMovie()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(
				Arrays.asList(new Movie("Filme 1", 1, 4d), new Movie("Filme 2", 1, 4d), new Movie("Filme 3", 1, 4d),
						new Movie("Filme 4", 1, 4d), new Movie("Filme 5", 1, 4d)));

		Location location = this.rentMovies(this.user, movies);

		assertEquals(14d, location.getBillingValue(), 0.001);
	}

	@Test
	public void mustGive100PercentDiscountOnSixthRentMovie()
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(
				Arrays.asList(new Movie("Filme 1", 1, 4d), new Movie("Filme 2", 1, 4d), new Movie("Filme 3", 1, 4d),
						new Movie("Filme 4", 1, 4d), new Movie("Filme 5", 1, 4d), new Movie("Filme 6", 1, 4d)));

		Location location = this.rentMovies(this.user, movies);

		assertEquals(14d, location.getBillingValue(), 0.001);
	}
}