package br.ec.builders;

import br.ec.domain.Movie;

public class MovieDataBuilder {

	private Movie movie;

	private MovieDataBuilder() {
	}

	public static MovieDataBuilder aMovie() {
		MovieDataBuilder builder = new MovieDataBuilder();
		builder.movie = new Movie();
		return builder;
	}

	public MovieDataBuilder withName(String name) {
		this.movie.setName(name);
		return this;
	}

	public MovieDataBuilder withNumberInStock(Integer stock) {
		this.movie.setStock(stock);
		return this;
	}

	public MovieDataBuilder withLocationPrice(Double locationPrice) {
		this.movie.setLocationPrice(locationPrice);
		return this;
	}

	public Movie builder() {
		return this.movie;
	}
}