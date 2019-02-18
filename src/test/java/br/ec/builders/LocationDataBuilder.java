package br.ec.builders;

import java.util.Date;
import java.util.List;

import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;

public class LocationDataBuilder {

	private Location location;

	private LocationDataBuilder() {
	}

	public static LocationDataBuilder aLocation() {
		LocationDataBuilder builder = new LocationDataBuilder();
		builder.location = new Location();
		return builder;
	}

	public LocationDataBuilder withUser(User user) {
		this.location.setUser(user);
		return this;
	}

	public LocationDataBuilder withMovies(List<Movie> movies) {
		this.location.setMovies(movies);
		return this;
	}

	public LocationDataBuilder withLocationDate(Date locationDate) {
		this.location.setLocationDate(locationDate);
		return this;
	}

	public LocationDataBuilder withReturningDate(Date returningDate) {
		this.location.setReturningDate(returningDate);
		return this;
	}

	public LocationDataBuilder withBillingValue(Double billingValue) {
		this.location.setBillingValue(billingValue);
		return this;
	}

	public Location build() {
		return this.location;
	}
}