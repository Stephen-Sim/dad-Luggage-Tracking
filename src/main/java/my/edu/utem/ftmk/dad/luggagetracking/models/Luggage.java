package my.edu.utem.ftmk.dad.luggagetracking.models;

import jakarta.persistence.*;

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
    private LuggageStatusType status;

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

	public LuggageStatusType getStatus() {
		return status;
	}

	public void setStatus(LuggageStatusType status) {
		this.status = status;
	}

	public PassengerFlight getPassengerFlight() {
		return passengerFlight;
	}

	public void setPassengerFlight(PassengerFlight passengerFlight) {
		this.passengerFlight = passengerFlight;
	}
}