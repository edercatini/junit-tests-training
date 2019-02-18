package br.ec.services;

import static br.ec.utils.DateUtils.addDays;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		Map<Integer, Double> discount = new HashMap<Integer, Double>();
		discount.put(2, 0.75);
		discount.put(3, 0.5);
		discount.put(4, 0.25);
		discount.put(5, 0.0);

		Double amountToPay = 0.0, discountRate = 0.0;
		Integer movieIndex = 0;

		for (Movie movie : movies) {
			discountRate = 1.0;

			if (discount.containsKey(movieIndex)) {
				discountRate = discount.get(movieIndex);
			}

			amountToPay += movie.getLocationPrice() * discountRate;
//			System.out.println("Amount To pay: " + amountToPay);
			movieIndex++;
		}

		Location location = new Location(user, movies, new Date(), addDays(new Date(), 1), amountToPay);

		// Saving location
		// TODO

		return location;
	}
}