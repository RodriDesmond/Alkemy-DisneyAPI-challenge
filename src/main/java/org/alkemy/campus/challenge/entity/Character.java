package org.alkemy.campus.challenge.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@JsonFilter("Details")
@Table(name = "characters")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class Character {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String img;
	@NotNull
	private String name;

	private Integer age;
	private Float weight;

	@NotNull
	private String story;

	@ManyToMany(mappedBy = "character", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Movie> movies;

}
