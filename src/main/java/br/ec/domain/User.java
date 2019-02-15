package br.ec.domain;

public class User {

	private String name;

	public User() {
	}

	public User(String name) {
		this.name = name;
	}

	public String getNome() {
		return this.name;
	}

	public User setNome(String name) {
		this.name = name;
		return this;
	}
}