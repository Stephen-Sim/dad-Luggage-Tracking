package my.edu.utem.ftmk.dad.luggagetracking.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import my.edu.utem.ftmk.dad.luggagetracking.models.Log;
import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;
import my.edu.utem.ftmk.dad.luggagetracking.models.StatusType;
import my.edu.utem.ftmk.dad.luggagetracking.repo.CheckPointRepository;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LogRepository;

@Service
public class LogService {
	
	private final LogRepository repo;
	private final CheckPointRepository checkPointRepo;
	
	public LogService(LogRepository repo, CheckPointRepository checkPointRepo)
	{
		this.repo = repo;
		this.checkPointRepo = checkPointRepo;
	}
	
	public void claimLogByLuggae(Luggage luggage)
	{
		StatusType status = new StatusType();
		status.setId(5L);
		
		Log mishandledLog = new Log();
		mishandledLog.setDateTime(LocalDateTime.now());
		mishandledLog.setCheckPoint(checkPointRepo.findCP4ByFlightId(luggage.getPassengerFlight().getFlight().getId()));
		mishandledLog.setLuggage(luggage);
		mishandledLog.setStatus(status);
		
		repo.save(mishandledLog);
	}
	
	public void reportLogByLuggae(Luggage luggage)
	{
		StatusType status = new StatusType();
		status.setId(7L);
		
		Log mishandledLog = new Log();
		mishandledLog.setDateTime(LocalDateTime.now());
		mishandledLog.setCheckPoint(checkPointRepo.findCP4ByFlightId(luggage.getPassengerFlight().getFlight().getId()));
		mishandledLog.setLuggage(luggage);
		mishandledLog.setStatus(status);
		
		repo.save(mishandledLog);
	}
}
