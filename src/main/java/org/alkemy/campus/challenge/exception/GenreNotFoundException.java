package org.alkemy.campus.challenge.exception;

public class GenreNotFoundException extends RuntimeException {
	public GenreNotFoundException(Integer id) {
		super("Genre id not found : " + id);
	}
}
