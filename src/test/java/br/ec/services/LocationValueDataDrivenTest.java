package br.ec.services;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ec.builders.MovieDataBuilder;
import br.ec.builders.UserDataBuilder;
import br.ec.domain.Location;
import br.ec.domain.Movie;
import br.ec.domain.User;
import br.ec.exceptions.EmptyMovieCollectionException;
import br.ec.exceptions.EmptyUserException;
import br.ec.exceptions.OutOfStockException;

@RunWith(Parameterized.class)
public class LocationValueDataDrivenTest {

	@Parameter(value = 0)
	public List<Movie> movies;

	@Parameter(value = 1)
	public Double locationValue;

	@Parameter(value = 2)
	public String scenario;

	private LocationService service;

	private User user;

	// Atributos utilizados como parâmetros dos testes
	private static Movie filme1 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 1").withNumberInStock(2).build();
	private static Movie filme2 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 2").withNumberInStock(2).build();
	private static Movie filme3 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 3").withNumberInStock(2).build();
	private static Movie filme4 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 4").withNumberInStock(2).build();
	private static Movie filme5 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 5").withNumberInStock(2).build();
	private static Movie filme6 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 6").withNumberInStock(2).build();
	private static Movie filme7 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 7").withNumberInStock(2).build();
	private static Movie filme8 = MovieDataBuilder.aMovie().withLocationPrice(4.0).withName("Movie 8").withNumberInStock(2).build();

	@Before
	public void setUp() {
		this.service = new LocationService(new CheckStockService(), new EmptyUserService(), new EmptyMovieCollectionService());
		this.user = UserDataBuilder.aUser().withName("User 1").build();
	}

	@Parameters(name = "Teste {index} = {2} - {1}")
	public static Collection<Object[]> parametersList() {
		return asList(new Object[][] {
			{ asList(filme1, filme2, filme3), 11.0, "25% no Terceiro Filme" },
			{ asList(filme1, filme2, filme3, filme4), 13.0, "50% no Quarto Filme" },
			{ asList(filme1, filme2, filme3, filme4, filme5), 14.0, "75% no Quinto Filme" },
			{ asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "100% no Sexto Filme" },
			{ asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "Nenhum Desconto no Sétimo Filme" },
			{ asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7, filme8), 22.0, "Nenhum Desconto no Oitavo Filme" }
		});
	}

	@Test
	public void mustCalculateLocationValue() throws OutOfStockException, EmptyUserException, EmptyMovieCollectionException {
		Location location = service.rentMovies(this.user, this.movies);
		assertEquals(this.locationValue, location.getBillingValue(), 0.001);
	}
}