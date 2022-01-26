package org.alkemy.campus.challenge.services;

import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository,
	                    CharacterRepository characterRepository) {
		this.movieRepository = movieRepository;
	}

	public void removeMovie(Long id, Movie movie) {
		Movie inDB = movieRepository.getById(id);
		movieRepository.delete(inDB);
	}
}
