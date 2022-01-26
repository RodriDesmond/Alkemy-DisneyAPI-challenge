package org.alkemy.campus.challenge.controller;

import org.alkemy.campus.challenge.dto.CharacterToMovieDto;
import org.alkemy.campus.challenge.dto.MovieToGenreDto;
import org.alkemy.campus.challenge.entity.Genre;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.repository.GenreRepository;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
	@Autowired
	GenreRepository genreRepository;
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	MovieService movieService;

	@GetMapping
	public ResponseEntity<?> listGenres(){
		List<Genre> genre = genreRepository.findAll();
		return new ResponseEntity<>(genre, HttpStatus.OK);
	}

	@PostMapping()
	public Genre save(@RequestParam("genreFile") MultipartFile image, @ModelAttribute Genre genre) {

		if (!image.isEmpty()) {

			Path imagesPath = Paths.get("src//main//resources//static//images//genres");
			String absolutPath = imagesPath.toFile().getAbsolutePath();
			try {
				byte[] bytes = image.getBytes();
				Path route = Paths.get(absolutPath + image.getOriginalFilename());
				Files.write(route, bytes);
				genre.setImg(image.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return genreRepository.save(genre);
	}

	@PostMapping("{genreId}/movie/{movieId}")
	public ResponseEntity<?> associateGenreToMovie(
			@PathVariable("genreId") Long genreId,
			@PathVariable("movieId") Long movieId,
			MovieToGenreDto movieToGenreDto) {
		genreRepository.findById(genreId);
		movieRepository.findById(movieId);
		return new ResponseEntity<>(movieService.associateMovieToGenre(genreId, movieId, movieToGenreDto), HttpStatus.CREATED);
	}
}
