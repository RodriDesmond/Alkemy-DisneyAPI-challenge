package org.alkemy.campus.challenge.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@JsonFilter("Details")
@Table(name = "characters")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Character {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String img;
	private String name;
	private Integer age;
	private Float weight;
	private String story;

	@ManyToMany(mappedBy = "character", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Movie> movies;

	public Character(){

	}
	public Character(Long id, String img, String name, Integer age, Float weight, String story, List<Movie> movies) {
		this.id = id;
		this.img = img;
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.story = story;
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "Character{" +
				"id=" + getId() +
				", img='" + getImg() + '\'' +
				", name='" + getName() + '\'' +
				", age=" + getAge() +
				", weight=" + getWeight() +
				", story='" + getStory() + '\'' +
				", movies=" + getMovies() +
				'}';
	}
}
