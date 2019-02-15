package br.ec.services;

import static br.ec.utils.DateUtils.incrementCurrentDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;

public class LocationServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	private User user = new User("Usuário 1");
	
	private List<Movie> movies = new ArrayList<Movie>();
	
	private LocationService service;
	
	private Format formatter;

	@Before
	public void setUp() {
		this.service = new LocationService();
		this.formatter = new SimpleDateFormat();
		this.movies.add(new Movie("Filme 1", 1, 5d));
	}

	@After
	public void tearDown() {

	}

	@Test
	public void mustBillASingleMoviePrice() {
		error.checkThat(this.allocateMovie(this.user, this.movies).getBillingValue(), is(equalTo(5d)));
	}

	@Test
	public void locationDateMustBeCurrentDate() {
		error.checkThat(formatter.format(this.allocateMovie(this.user, this.movies).getLocationDate()), is(equalTo(formatter.format(new Date()))));
	}

	@Test
	public void returningDateMustBeNextDay() {
		error.checkThat(formatter.format(this.allocateMovie(user, this.movies).getReturningDate()), is(equalTo(formatter.format(incrementCurrentDate(1)))));
	}

	private Location allocateMovie(User user, List<Movie> movies) {
		return service.allocateMovie(this.user, movies);
	}
}