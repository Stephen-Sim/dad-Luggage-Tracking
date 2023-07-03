package my.edu.utem.ftmk.dad.luggagetracking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * This class represents the CheckPoint entity, which corresponds to the 
 * "CheckPoints" table in the database.
 * 
 * @author simsh
 *
 */
@Entity
@Table(name = "CheckPoints")
public class CheckPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "FlightId", nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "CheckPointTypeId", nullable = false)
    private CheckPointType checkPointType;

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

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public CheckPointType getCheckPointType() {
		return checkPointType;
	}

	public void setCheckPointType(CheckPointType checkPointType) {
		this.checkPointType = checkPointType;
	}
}