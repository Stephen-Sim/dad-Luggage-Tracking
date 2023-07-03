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
 * This class represents the Luggage entity, which corresponds to the 
 * "Luggages" table in the database.
 * 
 * @author simsh
 *
 */
@Entity
@Table(name = "Luggages")
public class Luggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RFID", nullable = false, unique=true)
    private String rfid;

    @ManyToOne
    @JoinColumn(name = "StatusId", nullable = false)
    private StatusType status;
    
    @ManyToOne
    @JoinColumn(name = "PassengerFlightId", nullable = false)
    private PassengerFlight passengerFlight;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public PassengerFlight getPassengerFlight() {
		return passengerFlight;
	}

	public void setPassengerFlight(PassengerFlight passengerFlight) {
		this.passengerFlight = passengerFlight;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

}