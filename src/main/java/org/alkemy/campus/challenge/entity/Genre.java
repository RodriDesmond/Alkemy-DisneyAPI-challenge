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

	@Override
	public String toString() {
		return "Genre{" +
				"id=" + getId() +
				", img='" + getImg() + '\'' +
				", name=" + getName() +
				", movies=" + getMovies() +
				'}';
	}
}
