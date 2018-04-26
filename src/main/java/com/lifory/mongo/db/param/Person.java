package com.lifory.mongo.db.param;

public class Person {

	private int id;
	private String name;
	private String password;

	public int getId() {
		return id;
	}

	public Person setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Person setName(String name) {
		this.name = name;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Person setPassword(String password) {
		this.password = password;
		return this;
	}

}
