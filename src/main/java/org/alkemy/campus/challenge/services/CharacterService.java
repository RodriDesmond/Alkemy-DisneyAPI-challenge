package org.alkemy.campus.challenge.services;

import org.alkemy.campus.challenge.dto.CharacterDto;
import org.alkemy.campus.challenge.dto.CharacterToMovieDto;
import org.alkemy.campus.challenge.dto.CharacterUpdateDto;
import org.alkemy.campus.challenge.entity.Character;
import org.alkemy.campus.challenge.entity.Movie;

import java.util.List;

public interface CharacterService {

	CharacterUpdateDto updateCharacter(Long id, CharacterUpdateDto characterDto);

	void removeCharacter(Long id, Character character);

	Movie associateCharacterToMovie(Long characterId, Long movieId, CharacterToMovieDto characterToMovieDto);

	List<CharacterDto> findByAge(Integer age);
	CharacterDto findByName(String name);
	List<CharacterDto> findByMovieId(Long movieId);
}
