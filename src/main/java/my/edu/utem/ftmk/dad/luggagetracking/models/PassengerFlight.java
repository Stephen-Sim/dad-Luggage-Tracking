package my.edu.utem.ftmk.dad.luggagetracking.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PassengerFlights")
public class PassengerFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FlightId", nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "PassengerId", nullable = false)
    private Passenger passenger;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
}