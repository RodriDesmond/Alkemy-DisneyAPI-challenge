package org.alkemy.campus.challenge.services.impl;

import org.alkemy.campus.challenge.dto.CharacterDto;
import org.alkemy.campus.challenge.dto.CharacterToMovieDto;
import org.alkemy.campus.challenge.dto.CharacterUpdateDto;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.entity.Movie;
import org.alkemy.campus.challenge.mapper.CharacterMapper;
import org.alkemy.campus.challenge.repository.CharacterRepository;
import org.alkemy.campus.challenge.repository.MovieRepository;
import org.alkemy.campus.challenge.services.CharacterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

	private final CharacterRepository characterRepository;
	private final MovieRepository movieRepository;
	private final CharacterMapper mapper;

	public CharacterServiceImpl(CharacterRepository characterRepository,
	                            MovieRepository movieRepository,
	                            CharacterMapper mapper) {

		this.characterRepository = characterRepository;
		this.movieRepository = movieRepository;
		this.mapper = mapper;
	}

	@Override
	public CharacterUpdateDto updateCharacter(Long id, CharacterUpdateDto dto){

		Optional<Character> response = (characterRepository.findById(id).map(character -> {
			character.setName(dto.getName());
			character.setImg(dto.getImg());
			character.setStory(dto.getStory());
			character.setAge(dto.getAge());
			character.setWeight(dto.getWeight());
			characterRepository.save(character);
			return characterRepository.save(character);
		}));
		return mapper.toCharacterUpdateDto(response.get());
	}

	@Override
	public void removeCharacter(Long id, Character character) {
		Character inDB = characterRepository.getById(id);
		characterRepository.delete(inDB);
	}

	@Override
	public Movie associateCharacterToMovie(Long characterId, Long movieId, CharacterToMovieDto characterToMovieDto) {
		Character character = characterRepository.getById(characterId);
		Movie movie = movieRepository.getById(movieId);
		movie.addCharacter(character);
		return movieRepository.save(movie);
	}

	@Override
	public List<CharacterDto> findByAge(Integer age){
		return mapper.toCharacterDtoList(characterRepository.findByAge(age));
	}

	@Override
	public CharacterDto findByName(String name){
		return mapper.toCharacterDto(characterRepository.findByName(name));
	}

	@Override
	public List<CharacterDto> findByMovieId(Long movieId){
		return mapper.toCharacterDtoList(characterRepository.findByMovieId(movieId));
	}

}