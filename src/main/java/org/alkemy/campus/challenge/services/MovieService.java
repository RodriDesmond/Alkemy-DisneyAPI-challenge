package org.alkemy.campus.challenge.services;

import org.alkemy.campus.challenge.dto.CharacterToMovieDto;
import org.alkemy.campus.challenge.dto.MovieToGenreDto;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.entity.Genre;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.repository.GenreRepository;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	private final MovieRepository movieRepository;
	private final GenreRepository genreRepository;

	public MovieService(MovieRepository movieRepository,
	                    CharacterRepository characterRepository,
	                    GenreRepository genreRepository) {
		this.movieRepository = movieRepository;
		this.genreRepository = genreRepository;
	}

	public void removeMovie(Long id, Movie movie) {
		Movie inDB = movieRepository.getById(id);
		movieRepository.delete(inDB);
	}

	public Movie updateMovie(Long id, Movie movie){
		Movie inDB = movieRepository.getById(id);

		if(movie.getImg() != null){
			inDB.setImg(movie.getImg());
		}
		if(movie.getTitle() != null){
			inDB.setTitle(movie.getTitle());
		}
		if(movie.getCreationDate() != null){
			inDB.setCreationDate(movie.getCreationDate());
		}
		if(movie.getRating() != null) {
			inDB.setRating(movie.getRating());
		}
		return movieRepository.save(inDB);
	}

	public Movie associateMovieToGenre(Long genreId, Long movieId, MovieToGenreDto movieToGenreDto) {
		Genre genre = genreRepository.getById(genreId);
		Movie movie = movieRepository.getById(movieId);
		movie.addGenre(genre);
		return movieRepository.save(movie);
	}
}
