package br.ec.services;

import static br.ec.utils.DateUtils.addDays;

import java.util.Date;
import java.util.List;

import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;
import br.ec.exceptions.EmptyMovieCollectionException;
import br.ec.exceptions.EmptyUserException;
import br.ec.exceptions.OutOfStockException;

public class LocationService {

	private CheckStockService checkStockService;

	private EmptyUserService emptyUserService;

	private EmptyMovieCollectionService emptyMovieCollectionService;

	LocationService(CheckStockService checkStockService, EmptyUserService emptyUserService,
			EmptyMovieCollectionService emptyMovieCollectionService) {
		this.checkStockService = checkStockService;
		this.emptyUserService = emptyUserService;
		this.emptyMovieCollectionService = emptyMovieCollectionService;
	}

	public Location rentMovies(User user, List<Movie> movies)
			throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		this.emptyUserService.check(user);
		this.emptyMovieCollectionService.check(movies);
		this.checkStockService.inStock(movies);

		Double amountToPay = 0d;

		for (Movie movie : movies) {
			amountToPay += movie.getLocationPrice();
		}

		Location location = new Location(user, movies, new Date(), addDays(new Date(), 1), amountToPay);

		// Saving location
		// TODO

		return location;
	}
}