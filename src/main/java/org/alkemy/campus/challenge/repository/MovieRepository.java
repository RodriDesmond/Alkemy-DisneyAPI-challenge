package org.alkemy.campus.challenge.repository;

import org.alkemy.campus.challenge.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	Movie findByTitle(String name);
	List<Movie> findAllByOrderByTitleAsc();
	List<Movie> findAllByOrderByTitleDesc();
	@Query("SELECT m FROM Movie m join fetch m.genre g WHERE g.id = :idGenre")
	List<Movie> findByGenreId(@Param("idGenre")Long idGenre);
}
