package org.alkemy.campus.challenge.repository;

import org.alkemy.campus.challenge.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
