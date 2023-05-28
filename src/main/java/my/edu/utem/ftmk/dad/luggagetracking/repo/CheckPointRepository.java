package my.edu.utem.ftmk.dad.luggagetracking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.edu.utem.ftmk.dad.luggagetracking.models.CheckPoint;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long>{
	@Query("SELECT cp FROM CheckPoint cp WHERE cp.name = :name AND cp.checkPointType.id = 2")
    CheckPoint findTruckCheckPointByName(@Param("name") String name);
}