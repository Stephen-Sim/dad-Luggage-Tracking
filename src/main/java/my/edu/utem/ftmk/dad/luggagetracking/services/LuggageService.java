package my.edu.utem.ftmk.dad.luggagetracking.services;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.edu.utem.ftmk.dad.luggagetracking.models.Log;
import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;
import my.edu.utem.ftmk.dad.luggagetracking.models.StatusType;
import my.edu.utem.ftmk.dad.luggagetracking.repo.CheckPointRepository;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LogRepository;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LuggageRepository;

@Service
public class LuggageService {
	
	private final LuggageRepository luggageRepo;
	private final CheckPointRepository checkPointRepo;
	private final LogRepository logRepo;
	
	@Autowired
    public LuggageService(LuggageRepository luggageRepo, LogRepository logRepo, CheckPointRepository checkPointRepo) {
        this.luggageRepo = luggageRepo;
		this.checkPointRepo = checkPointRepo;
        this.logRepo = logRepo;
    }
	
	public void updateLuggageStatus(Log log)
	{
		try
		{
			StatusType status = new StatusType();
			Luggage luggage = luggageRepo.findById(log.getLuggage().getId()).orElseThrow();
			
			// update the luggage status
			if(luggage.getPassengerFlight().getFlight().getId() != log.getCheckPoint().getFlight().getId())
			{
				status.setId(8L);
				
				luggage.setStatus(status);
				luggageRepo.save(luggage);				
				
				
				Log mishandledLog = new Log();
				mishandledLog.setDateTime(LocalDateTime.now());
				mishandledLog.setCheckPoint(log.getCheckPoint());
				mishandledLog.setLuggage(luggage);
				mishandledLog.setStatus(status);
				
				logRepo.save(mishandledLog);
				
				return;
			}
			
			if(log.getCheckPoint().getCheckPointType().getId() == 2)
			{
				// RFID sticker is scanned at CP1
				status.setId(2L);
			}
			else if(log.getCheckPoint().getCheckPointType().getId() == 3)
			{
				// RFID sticker is scanned at CP2
				status.setId(3L);
			}
			else if(log.getCheckPoint().getCheckPointType().getId() == 4)
			{
				// RFID sticker is scanned at CP3
				status.setId(4L);
				
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
	                @Override
	                public void run() {
	                	if (luggage.getStatus().getId() == 4L) {
	                        StatusType delayedStatus = new StatusType();
	                         delayedStatus.setId(6L);
	                        luggage.setStatus(delayedStatus);
	                        luggageRepo.save(luggage);
	                        
	                        var cp4CheckPiot = checkPointRepo.findCP4ByFlightId(luggage.getPassengerFlight().getFlight().getId());
	                        
	                        Log mishandledLog = new Log();
	        				mishandledLog.setDateTime(LocalDateTime.now());
	        				mishandledLog.setCheckPoint(cp4CheckPiot);
	        				mishandledLog.setLuggage(luggage);
	        				mishandledLog.setStatus(delayedStatus);
	        				
	        				logRepo.save(mishandledLog);
	                    }
	                }
	            }, 60000); 
			}
			
			luggage.setStatus(status);
			luggageRepo.save(luggage);
		}
		catch(Exception err)
		{
			System.out.println(err.getMessage());
		}
	}
}
