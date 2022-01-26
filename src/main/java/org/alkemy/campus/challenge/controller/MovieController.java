package org.alkemy.campus.challenge.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.exception.CharacterNotFoundException;
import org.alkemy.campus.challenge.exception.GenreNotFoundException;
import org.alkemy.campus.challenge.exception.MovieNotFoundException;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MovieService movieService;

	@GetMapping
	public ResponseEntity<?> listCharacter(
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "genre", required = false) Integer idGenre
	){
		List<Movie> movie = movieRepository.findAll();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("title","img");
		FilterProvider filters = new SimpleFilterProvider().addFilter("MovieDetails", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(movie);
		mapping.setFilters(filters);
		if(name != null){
			return new ResponseEntity<>(movieRepository.findByTitle(name), HttpStatus.OK);
		} else if (Objects.nonNull(idGenre)) {
			return new ResponseEntity<>(movieRepository.findByGenreId(idGenre), HttpStatus.OK);
		}
		else if (Objects.equals(order, "asc")){
			return new ResponseEntity<>(movieRepository.findAllByOrderByTitleAsc(), HttpStatus.OK);
		}
		else if (Objects.equals(order, "desc")){
			return new ResponseEntity<>(movieRepository.findAllByOrderByTitleDesc(), HttpStatus.OK);
		}
		return new ResponseEntity<>(mapping, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getMovieDetails(@PathVariable Long id) {
		return new ResponseEntity<>(movieRepository.findById(id)
				.orElseThrow(() -> new MovieNotFoundException(id)),HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie) {
		return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	@ResponseStatus( HttpStatus.NO_CONTENT)
	public void removeCharacter(@PathVariable Long id){
		Movie movie = movieRepository.getById(id);
		this.movieService.removeMovie(id, movie);
	}
}
