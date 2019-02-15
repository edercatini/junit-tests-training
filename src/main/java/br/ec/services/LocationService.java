package br.ec.services;

import static br.ec.utils.DateUtils.addDays;

import java.util.Date;
import java.util.List;

import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;
import br.ec.exceptions.OutOfStockException;

public class LocationService {

	private CheckStockService checkStockService;

	LocationService(CheckStockService checkStockService) {
		this.checkStockService = checkStockService;
	}

	public Location allocateMovie(User user, List<Movie> movies) throws OutOfStockException {
		
		checkStockService.inStock(movies);
		
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