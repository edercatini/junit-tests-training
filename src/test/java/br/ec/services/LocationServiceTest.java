package br.ec.services;

import static br.ec.utils.DateUtils.addDays;
import static br.ec.utils.DateUtils.incrementCurrentDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ec.builders.MovieDataBuilder;
import br.ec.builders.UserDataBuilder;
import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;
import br.ec.exceptions.EmptyMovieCollectionException;
import br.ec.exceptions.EmptyUserException;
import br.ec.exceptions.OutOfStockException;
import br.ec.utils.DateUtils;

public class LocationServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Rule
	public ErrorCollector error = new ErrorCollector();

	private User user = UserDataBuilder.aUser().withName("Usu�rio 1").build();

	private LocationService locationService = new LocationService(new CheckStockService(), new EmptyUserService(),
			new EmptyMovieCollectionService());

	private Format formatter = new SimpleDateFormat("d/M/Y");

	private Location rentMovies(User user, List<Movie> movies) throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		return locationService.rentMovies(user, movies);
	}

	@Test
	public void mustBillASingleMoviePrice() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 1").withNumberInStock(2).build()
		));

		error.checkThat(this.rentMovies(this.user, movies).getBillingValue(), is(equalTo(5d)));
	}

	@Test
	public void locationDateMustBeCurrentDate() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 1").withNumberInStock(2).build()
		));
		
		error.checkThat(this.formatter.format(this.rentMovies(this.user, movies).getLocationDate()), is(equalTo(formatter.format(new Date()))));
	}

	@Test
	public void returningDateMustBeNextDay() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 1").withNumberInStock(2).build()
		));

		error.checkThat(this.formatter.format(this.rentMovies(user, movies).getReturningDate()), is(equalTo(formatter.format(incrementCurrentDate(1)))));
	}

	@Test
	public void mustNotRentIfStockIsInsufficientForAtLeastOneMovie() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 1").withNumberInStock(2).build(),
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 2").withNumberInStock(0).build()
		));

		this.exception.expect(OutOfStockException.class);
		this.exception.expectMessage("Insufficient stock");
		this.rentMovies(this.user, movies);
	}

	@Test
	public void mustNotRentIfUserIsInvalid() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 1").withNumberInStock(2).build(),
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 2").withNumberInStock(0).build()
		));

		this.exception.expect(EmptyUserException.class);
		this.exception.expectMessage("Invalid User");
		this.rentMovies(null, movies);
	}

	@Test
	public void mustNotRentIfMovieCollectionIsEmpty() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		this.exception.expect(EmptyMovieCollectionException.class);
		this.exception.expectMessage("Invalid Movie Collection");
		this.rentMovies(this.user, new ArrayList<Movie>());
	}

	@Test
	public void mustReturnOnMondayIfLocationHappensOnSaturday() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		assumeTrue(DateUtils.verifyWeekDay(new Date(), Calendar.SATURDAY));

		List<Movie> movies = new ArrayList<Movie>(Arrays.asList(
			MovieDataBuilder.aMovie().withLocationPrice(5d).withName("Movie 1").withNumberInStock(2).build()
		));

		Location location = this.rentMovies(this.user, movies);
		assertEquals(this.formatter.format(location.getReturningDate()), this.formatter.format(addDays(new Date(), 2)));
	}
}