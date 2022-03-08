package org.alkemy.campus.challenge.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "genres",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "name"),
				@UniqueConstraint(columnNames = "img")
		})
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String img;

	@Enumerated(EnumType.STRING)
	@Column(unique = true)
	private EGenres name;

	@ManyToMany(mappedBy = "genre", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Movie> movies;
}
