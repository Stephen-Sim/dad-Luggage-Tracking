package my.edu.utem.ftmk.dad.luggagetracking.models;

import jakarta.persistence.*;

@Entity
@Table(name = "LuggageStatusTypes")
public class LuggageStatusType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}