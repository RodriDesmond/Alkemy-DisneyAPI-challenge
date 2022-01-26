package org.alkemy.campus.challenge.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "genre",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "name"),
				@UniqueConstraint(columnNames = "img")
		})
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Integer id;

	private String img;

	@Enumerated(EnumType.STRING)
	private EGenres name;

	@ManyToMany(mappedBy = "genres")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Movie> movies;
}
