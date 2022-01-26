package org.alkemy.campus.challenge.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.exception.MovieNotFoundException;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			@RequestParam(name = "genre", required = false) Long idGenre
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

	@PostMapping()
	public Movie save(@RequestParam("movieFile") MultipartFile image, @ModelAttribute Movie movie){

		if(!image.isEmpty()){

			Path imagesPath = Paths.get("src//main//resources//static//images//movies");
			String absolutPath = imagesPath.toFile().getAbsolutePath();
			try {
				byte[] bytes = image.getBytes();
				Path route = Paths.get(absolutPath + image.getOriginalFilename());
				Files.write(route, bytes);
				movie.setImg(image.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return movieRepository.save(movie);
	}
	@PutMapping("{id}")
	public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie){
		return this.movieService.updateMovie(id, movie);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus( HttpStatus.NO_CONTENT)
	public void removeCharacter(@PathVariable Long id){
		Movie movie = movieRepository.getById(id);
		this.movieService.removeMovie(id, movie);
	}
}
