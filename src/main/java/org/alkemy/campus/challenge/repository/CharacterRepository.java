package org.alkemy.campus.challenge.repository;

import org.alkemy.campus.challenge.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
	Character findByName(String name);
	List<Character> findByAge(Integer age);
}
