package my.edu.utem.ftmk.dad.luggagetracking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;

@Repository
public interface LuggageRepository extends JpaRepository<Luggage, Long>{
	@Query("SELECT l FROM Luggage l WHERE l.passengerFlight.id = :passengerFlightId")
    List<Luggage> findByPassengerFlightId(@Param("passengerFlightId") long passengerFlightId);
	
	@Query("SELECT l FROM Luggage l WHERE l.rfid= :rfid")
    Luggage findByRFID(@Param("rfid") String rfid);
	
	@Query("SELECT l FROM Luggage l WHERE l.passengerFlight.flight.flightNo = :flightNo AND l.status.id = 6")
	List<Luggage> findUnclaimedLuggage(@Param("flightNo") String flightNo);

	@Query("SELECT l FROM Luggage l WHERE l.passengerFlight.flight.flightNo = :flightNo AND l.status.id = 7")
	List<Luggage> findMissingLuggage(@Param("flightNo") String flightNo);
}
