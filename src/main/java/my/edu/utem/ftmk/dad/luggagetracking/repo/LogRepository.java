package my.edu.utem.ftmk.dad.luggagetracking.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import my.edu.utem.ftmk.dad.luggagetracking.models.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    
	@Query("SELECT l FROM Log l WHERE l.luggage.id = :luggageId")
    List<Log> findByLuggageId(@Param("luggageId") long luggageId);
}
