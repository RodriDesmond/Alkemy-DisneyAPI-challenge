package org.alkemy.campus.challenge.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

	@NotNull
	private String img;
	@NotNull
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


	@JoinTable(	name = "genre_movie",
			joinColumns = @JoinColumn(name = "id_movie", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "id_genre", nullable = false))
	@ManyToMany(fetch = FetchType.LAZY,cascade ={
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	private List<Genre> genre = new ArrayList<>();

	public void addCharacter(Character character){
		if(this.character == null){
			this.character = new ArrayList<>();
		}
		this.character.add(character);
	}

	public void addGenre(Genre genre){
		if(this.genre == null){
			this.genre = new ArrayList<>();
		}
		this.genre.add(genre);
	}
	public Movie() {
	}

	public Movie(Long id, String img, String title, EType type, Date creationDate, Integer rating, List<Character> character, List<Genre> genre) {
		this.id = id;
		this.img = img;
		this.title = title;
		this.type = type;
		this.creationDate = creationDate;
		this.rating = rating;
		this.character = character;
		this.genre = genre;
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
				", genre=" + getGenre() +
				'}';
	}
}
