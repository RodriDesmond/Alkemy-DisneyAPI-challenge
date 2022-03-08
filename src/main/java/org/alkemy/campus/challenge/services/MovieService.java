package org.alkemy.campus.challenge.services;

import org.alkemy.campus.challenge.dto.MovieToGenreDto;
import org.alkemy.campus.challenge.entity.Movie;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {

	void removeMovie(Long id, Movie movie);

	Movie updateMovie(Long id, Movie movie);

	Movie associateMovieToGenre(Long genreId, Long movieId, MovieToGenreDto movieToGenreDto);
}
