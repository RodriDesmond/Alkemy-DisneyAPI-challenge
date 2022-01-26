package org.alkemy.campus.challenge.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.*;

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

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date creationDate;

	@Min(value = 1, message = "Rating must be greater or equal to 1")
	@Max(value = 5, message = "Rating must be less or equal to 5")
	private Integer rating;

	@JoinTable(name = "character_movie",
			joinColumns = @JoinColumn(name = "id_movie", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "id_character", nullable = false))
	@ManyToMany(fetch = FetchType.LAZY, cascade ={
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	private List<Character> character = new ArrayList<>();;


	@ManyToMany(fetch = FetchType.LAZY,cascade ={
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
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

	public Movie() {
	}

	public Movie(Long id, String img, String title, EType type, Date creationDate, Integer rating, List<Character> character, Set<Genre> genres) {
		this.id = id;
		this.img = img;
		this.title = title;
		this.type = type;
		this.creationDate = creationDate;
		this.rating = rating;
		this.character = character;
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"id=" + getId() +
				", img='" + getImg() + '\'' +
				", title='" + getTitle() + '\'' +
				", type=" + getType() +
				", creationDate=" + getCreationDate() +
				", rating=" + getRating() +
				", character=" + getCharacter() +
				", genres=" + getGenres() +
				'}';
	}
}
