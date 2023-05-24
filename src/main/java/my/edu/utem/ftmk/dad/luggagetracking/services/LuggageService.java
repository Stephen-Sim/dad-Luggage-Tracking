package my.edu.utem.ftmk.dad.luggagetracking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.edu.utem.ftmk.dad.luggagetracking.models.Log;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LuggageRepository;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LuggageStatusTypeRepository;

@Service
public class LuggageService {
	private final LuggageRepository luggageRepo;
	
	@Autowired
    public LuggageService(LuggageRepository luggageRepo) {
        this.luggageRepo = luggageRepo;
    }
	
	public void updateLuggageStatus(Log log)
	{
		try
		{
			var luggage = log.getLuggage();
			
			// update the luggage status
			if(luggage.getPassengerFlight().getFlight() != log.getCheckPoint().getFlight())
			{
				luggage.getStatus().setId((long) 5);
				luggageRepo.save(luggage);
				return;
			}
			
			if(log.getCheckPoint().getId() == 1)
			{
				// RFID sticker is scanned at CP1
				luggage.getStatus().setId((long) 2);
			}
			else if(log.getCheckPoint().getId() == 2)
			{
				// RFID sticker is scanned at CP2
				luggage.getStatus().setId((long) 3);
			}
			else if(log.getCheckPoint().getId() == 3)
			{
				// RFID sticker is scanned at CP3
				luggage.getStatus().setId((long) 4);
			}
			else if(log.getCheckPoint().getId() == 4)
			{
				// RFID sticker is scanned at CP4
				// and the luggage is claimed by passenger
				luggage.getStatus().setId((long) 5);
			}
			
			luggageRepo.save(luggage);
		}
		catch(Exception err)
		{
			System.out.println(err.getMessage());
		}
	}
}
