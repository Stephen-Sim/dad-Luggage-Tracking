package my.edu.utem.ftmk.dad.luggagetracking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.edu.utem.ftmk.dad.luggagetracking.models.PassengerFlight;

public interface PassengerFlightRepository extends JpaRepository<PassengerFlight, Long>{
	@Query("SELECT pf FROM PassengerFlight pf WHERE pf.passenger.identityNo = :identityNo AND pf.flight.flightNo = :flightNo")
    PassengerFlight findPassengerFlightByIdentityNoAndFlightNo(@Param("identityNo") String identityNo, @Param("flightNo") String flightNo);
}