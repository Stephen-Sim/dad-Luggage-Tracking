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
	
	@Query(value = "SELECT l.RFID, " +
		    "MAX(CASE WHEN cpt.Id = 2 THEN log.DateTime END) AS cp1, " +
		    "MAX(CASE WHEN cpt.Id = 3 THEN log.DateTime END) AS cp2, " +
		    "MAX(CASE WHEN cpt.Id = 4 THEN log.DateTime END) AS cp3, " +
		    "MAX(CASE WHEN cpt.Id = 5 THEN log.DateTime END) AS cp4, " +
		    "s.Name AS status " +
		    "FROM passengerflights pf " +
		    "LEFT JOIN luggages l ON l.PassengerFlightId = pf.Id " +
		    "LEFT JOIN flights f ON f.Id = pf.FlightId " +
		    "LEFT JOIN statustypes s ON l.StatusId = s.Id " +
		    "INNER JOIN logs log ON l.Id = log.LuggageId " +
		    "INNER JOIN checkpoints c ON log.CheckPointId = c.Id " +
		    "INNER JOIN checkpointtypes cpt ON c.CheckPointTypeId = cpt.Id " +
		    "WHERE f.FlightNo = :flightNo AND s.Id != 8 " +
		    "GROUP BY l.RFID, status;", nativeQuery = true)
    List<Object[]> findLugageCheckPointTimeByFlightNo(@Param("flightNo") String flightNo);
    
    @Query(value = "SELECT sub.RFID, sub.DateTime, sub.CheckPointName, sub.ArrivalLocation " +
            "FROM ( " +
            "  SELECT l.RFID, log.DateTime, c.Name AS CheckPointName, f.ArrivalLocation, " +
            "         ROW_NUMBER() OVER (PARTITION BY l.RFID ORDER BY log.DateTime DESC) AS rn " +
            "  FROM passengerflights pf " +
            "  LEFT JOIN luggages l ON l.PassengerFlightId = pf.Id " +
            "  LEFT JOIN flights f ON f.Id = pf.FlightId " +
            "  LEFT JOIN statustypes s ON l.StatusId = s.Id " +
            "  INNER JOIN logs log ON l.Id = log.LuggageId " +
            "  INNER JOIN checkpoints c ON log.CheckPointId = c.Id " +
            "  INNER JOIN checkpointtypes cpt ON c.CheckPointTypeId = cpt.Id " +
            "  WHERE f.FlightNo = :flightNo AND s.Id = 8 " +
            ") sub " +
            "WHERE sub.rn = 1;", nativeQuery = true)
    List<Object[]> findMishandledLuggage(@Param("flightNo") String flightNo);
}
