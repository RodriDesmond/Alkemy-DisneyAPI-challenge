package org.alkemy.campus.challenge.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "movie")
@JsonFilter("MovieDetails")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String img;
	private String title;
	@Enumerated(EnumType.STRING)
	private EType type;
	private LocalDate fechaDeCreacion;
	@Min(value = 1, message = "Rating must be greater or equal to 1")
	@Max(value = 5, message = "Rating must be less or equal to 5")
	private Integer rating;

	@JoinTable(name = "character_movie",
			joinColumns = @JoinColumn(name = "character_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "movie_id", nullable = false))
	@ManyToMany(cascade ={
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	private List<Character> character;


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "movie_genres",
			joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> genres = new HashSet<>();

		public void addCharacter(Character character){
		if(this.character == null){
			this.character = new ArrayList<>();
		}
		this.character.add(character);
	}

}
