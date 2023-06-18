package my.edu.utem.ftmk.dad.luggagetracking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.edu.utem.ftmk.dad.luggagetracking.models.CheckPoint;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long>{
	
	@Query("SELECT cp FROM CheckPoint cp WHERE cp.flight.flightNo = :flightNo "
			+ "AND cp.checkPointType.id = :checkPoinTypetId")
	CheckPoint findByFlightNoAndcheckPoinTypetId(@Param("flightNo") 
			String flightNo, @Param("checkPoinTypetId") long checkPoinTypetId);
	
	@Query("SELECT cp FROM CheckPoint cp WHERE cp.flight.flightNo = :flightNo")
	List<CheckPoint> findByFlightNo(@Param("flightNo") String flightNo);
	
	@Query("SELECT cp FROM CheckPoint cp WHERE cp.flight.id = :id "
			+ "AND cp.checkPointType.id = 5")
    CheckPoint findCP4ByFlightId(@Param("id") long id);
}