package br.ec.domain;

public class Movie {

	private String name;
	private Integer stock;
	private Double locationPrice;

	public Movie() {
	}

	public Movie(String name, Integer stock, Double locationPrice) {
		this.name = name;
		this.stock = stock;
		this.locationPrice = locationPrice;
	}

	public String getName() {
		return this.name;
	}

	public Movie setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getStock() {
		return this.stock;
	}

	public Movie setStock(Integer stock) {
		this.stock = stock;
		return this;
	}

	public Double getLocationPrice() {
		return this.locationPrice;
	}

	public Movie setLocationPrice(Double locationPrice) {
		this.locationPrice = locationPrice;
		return this;
	}
}