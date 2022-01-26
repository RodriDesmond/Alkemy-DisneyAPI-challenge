package org.alkemy.campus.challenge.exception;

public class CharacterNotFoundException extends RuntimeException {
	public CharacterNotFoundException(Long id) {
		super("Character id not found : " + id);
	}
}
