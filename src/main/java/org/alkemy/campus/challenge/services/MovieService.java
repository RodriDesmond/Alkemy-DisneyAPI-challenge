package org.alkemy.campus.challenge.services;

import org.alkemy.campus.challenge.dto.CharacterToMovieDto;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	private final MovieRepository movieRepository;
	private final CharacterRepository characterRepository;
	public MovieService(MovieRepository movieRepository,
	                    CharacterRepository characterRepository) {
		this.movieRepository = movieRepository;
		this.characterRepository = characterRepository;
	}
}
