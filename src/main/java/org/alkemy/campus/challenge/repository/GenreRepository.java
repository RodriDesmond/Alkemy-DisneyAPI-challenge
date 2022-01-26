package org.alkemy.campus.challenge.repository;

import org.alkemy.campus.challenge.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
