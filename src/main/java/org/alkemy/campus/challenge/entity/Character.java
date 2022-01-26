package org.alkemy.campus.challenge.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

	@ManyToMany(mappedBy = "character")
	private List<Movie> movies;
}
