package org.alkemy.campus.challenge.mapper;

import org.alkemy.campus.challenge.dto.CharacterDto;
import org.alkemy.campus.challenge.dto.CharacterUpdateDto;
import org.alkemy.campus.challenge.entity.Character;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CharacterMapper {
	CharacterUpdateDto toCharacterUpdateDto(Character character);

	Character toCharacter(CharacterUpdateDto characterUpdateDto);

	List<CharacterDto> toCharacterDtoList(List<Character> character);

	CharacterDto toCharacterDto(Character character);
}
