package org.alkemy.campus.challenge.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
	@Autowired
	private MovieRepository movieRepository;

	@GetMapping
	public ResponseEntity<?> listCharacter(){
		List<Movie> movie = movieRepository.findAll();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("title","img");
		FilterProvider filters = new SimpleFilterProvider().addFilter("MovieDetails", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(movie);
		mapping.setFilters(filters);
		return new ResponseEntity<>(mapping, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie) {
		return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.CREATED);
	}
}
