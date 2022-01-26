package org.alkemy.campus.challenge.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.alkemy.campus.challenge.dto.CharacterToMovieDto;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.exception.CharacterNotFoundException;
import org.alkemy.campus.challenge.repository.CharacterRepository;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.services.CharacterService;
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
@RequestMapping("/characters")
public class CharacterController {

	@Autowired
	private CharacterService characterService;
	@Autowired
	private CharacterRepository characterRepository;
	@Autowired
	private MovieRepository movieRepository;

	@GetMapping
	public ResponseEntity<?> listCharacter(
			@RequestParam(name = "age", required = false) Integer age,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "idMovie", required = false) Long idMovie
	){
		List<Character> character = characterRepository.findAll();
		SimpleBeanPropertyFilter characterFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","img");
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter("Details", characterFilter);
		MappingJacksonValue mapping = new MappingJacksonValue(character);
		mapping.setFilters(filters);
		if(age != null){
			return new ResponseEntity<>(characterRepository.findByAge(age), HttpStatus.OK);
		} else if (Objects.nonNull(name)) {
			return new ResponseEntity<>(characterRepository.findByName(name), HttpStatus.OK);
		} else if (Objects.nonNull(idMovie)) {
		return new ResponseEntity<>(characterRepository.findByMovieId(idMovie), HttpStatus.OK);
	}
		return new ResponseEntity<>(mapping, HttpStatus.OK);
	}
	@PostMapping()
	public Character save(@RequestParam("file") MultipartFile image, @ModelAttribute Character character){

		if(!image.isEmpty()){

			Path imagesPath = Paths.get("src//main//resources//static//images//characters");
			String absolutPath = imagesPath.toFile().getAbsolutePath();
			try {
				byte[] bytes = image.getBytes();
				Path route = Paths.get(absolutPath + image.getOriginalFilename());
				Files.write(route, bytes);
				character.setImg(image.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return characterRepository.save(character);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getCharacterDetails(@PathVariable Long id) {
		return new ResponseEntity<>(characterRepository.findById(id)
				.orElseThrow(() -> new CharacterNotFoundException(id)), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public Character updateCharacter(@PathVariable Long id, @RequestBody Character character){
		return this.characterService.updatePersonaje(id, character);
	}

	@DeleteMapping("{id}")
	@ResponseStatus( HttpStatus.NO_CONTENT)
	public void removeCharacter(@PathVariable Long id){
		Character character = characterRepository.getById(id);
		this.characterService.removeCharacter(id, character);
	}

	@PostMapping("{characterId}/movie/{movieId}")
	public ResponseEntity<?> associateCharacterToMovie(
			@PathVariable("characterId") Long characterId,
	        @PathVariable("movieId") Long movieId,
			CharacterToMovieDto characterToMovieDto) {
		characterRepository.findById(characterId);
		movieRepository.findById(movieId);
		return new ResponseEntity<>(characterService.associateCharacterToMovie(characterId, movieId, characterToMovieDto), HttpStatus.CREATED);
	}
}
