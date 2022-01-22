package org.alkemy.campus.challenge.services;

import org.alkemy.campus.challenge.dto.CharacterToMovieDto;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

	private final CharacterRepository characterRepository;
	private final MovieRepository movieRepository;

	public CharacterService(CharacterRepository characterRepository, MovieRepository movieRepository) {

		this.characterRepository = characterRepository;
		this.movieRepository = movieRepository;
	}

	public Character updatePersonaje(Long id, Character character){
		Character inDB = characterRepository.getById(id);

		if(character.getImg() != null){
			inDB.setImg(character.getImg());
		}
		if(character.getName() != null){
			inDB.setName(character.getName());
		}
		if(character.getStory() != null){
			inDB.setStory(character.getStory());
		}
		if(character.getWeight() != null){
			inDB.setWeight(character.getWeight());
		}
		if(character.getAge() != null){
			inDB.setAge(character.getAge());
		}
		if(character.getMovies() != null) {
			inDB.getMovies().addAll(character.getMovies());
		}
		return characterRepository.save(inDB);
	}

	public void removeCharacter(Long id, Character character) {
		Character inDB = characterRepository.getById(id);
		characterRepository.delete(inDB);
	}

	public Movie associateCharacterToMovie(Long characterId, Long movieId, CharacterToMovieDto characterToMovieDto) {
		Character character = characterRepository.getById(characterId);
		Movie movie = movieRepository.getById(movieId);
		movie.addCharacter(character);
		return movieRepository.save(movie);
	}
}
