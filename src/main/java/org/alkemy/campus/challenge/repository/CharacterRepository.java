package org.alkemy.campus.challenge.repository;

import org.alkemy.campus.challenge.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
	Character findByName(String name);
	List<Character> findByAge(Integer age);
	@Query("SELECT c FROM Character c join fetch c.movies m WHERE m.id = :idMovie")
	List<Character> findByMovieId(@Param("idMovie")Long idMovie);

	@Query(value = "SELECT c.name,c.img FROM characters c",nativeQuery = true)
	public ArrayList<Object[]> getAll();
}
