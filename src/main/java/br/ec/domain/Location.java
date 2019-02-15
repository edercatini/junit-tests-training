package br.ec.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Location {

	private User user;
	private List<Movie> movies = new ArrayList<Movie>();
	private Date locationDate;
	private Date returningDate;
	private Double billingValue;

	public Location() {
	}
	
	public Location(User user, List<Movie> movies, Date locationDate, Date returningDate, Double billingValue) {
		this.user = user;
		this.movies = movies;
		this.locationDate = locationDate;
		this.returningDate = returningDate;
		this.billingValue = billingValue;
	}
	
	public User getUser() {
		return this.user;
	}

	public Location setUser(User user) {
		this.user = user;
		return this;
	}

	public List<Movie> getMovie() {
		return this.movies;
	}

	public Location setMovie(Movie movie) {
		this.movies.add(movie);
		return this;
	}

	public Date getLocationDate() {
		return this.locationDate;
	}

	public Location setLocationDate(Date locationDate) {
		this.locationDate = locationDate;
		return this;
	}

	public Date getReturningDate() {
		return this.returningDate;
	}

	public Location setReturningDate(Date returningDate) {
		this.returningDate = returningDate;
		return this;
	}

	public Double getBillingValue() {
		return this.billingValue;
	}

	public Location setBillingValue(Double billingValue) {
		this.billingValue = billingValue;
		return this;
	}
}