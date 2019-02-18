package br.ec.builders;

import br.ec.domain.User;

public class UserDataBuilder {

	private User user;

	private UserDataBuilder() {
	}

	public static UserDataBuilder aUser() {
		UserDataBuilder builder = new UserDataBuilder();
		builder.user = new User();
		return builder;
	}

	public UserDataBuilder withName(String name) {
		this.user.setName(name);
		return this;
	}

	public User build() {
		return this.user;
	}
}