package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;
import my.edu.utem.ftmk.dad.luggagetracking.models.StatusType;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LuggageRepository;
import my.edu.utem.ftmk.dad.luggagetracking.services.LogService;

@RestController
@RequestMapping("api/luggage")
public class LuggageRestController {

	@Autowired
	private final LuggageRepository repo;
	private final LogService logService;
	
	public LuggageRestController(LuggageRepository repo, LogService logService)
	{
		this.repo = repo;
		this.logService = logService;
	}
	
	@GetMapping("passengerFlight/{passengerFlightId}")
	public List<Luggage> getLuggagesByPassengerFlightId(@PathVariable long passengerFlightId)
	{
		return repo.findByPassengerFlightId(passengerFlightId);
	}
	
	@GetMapping("{luggageId}")
	public Luggage getLuggagesById(@PathVariable long luggageId)
	{
		return repo.findById(luggageId).get();
	}
	
	@GetMapping("getLuggageByRFID")
	public Luggage getLuggageByRFID(String rfid)
	{
		return repo.findByRFID(rfid);
	}
	
	@PostMapping("/claimLuggage/{id}")
    public ResponseEntity<String> claimLuggage(@PathVariable long id) {
        
		try {
			
			Luggage luggage = repo.findById(id).orElseThrow();
			
			StatusType status = new StatusType();
			status.setId(5L);
			
			luggage.setStatus(status);
			
			repo.save(luggage);
			
			logService.claimLogByLuggae(luggage);
			
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		} 
		catch(Exception err) {
			
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail to claim");
		}
    }
	
	@PostMapping("/reportMissing/{id}")
    public ResponseEntity<String> reportMissing(@PathVariable long id) {
        
		try {
			
			Luggage luggage = repo.findById(id).orElseThrow();
			
			StatusType status = new StatusType();
			status.setId(7L);
			
			luggage.setStatus(status);
			
			repo.save(luggage);
			
			logService.reportLogByLuggae(luggage);
			
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		} 
		catch(Exception err) {
			
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail to claim");
		}
    }
	
	@GetMapping("/missingAndUnclaim")
	public ResponseEntity<Map<String, List<Luggage>>> searchLuggage(@RequestParam("flightNo") String flightNo) {
	    if (flightNo == null || flightNo.isEmpty()) {
	        return ResponseEntity.badRequest().build();
	    }
	    
	    try {
	        List<Luggage> unclaimedLuggage = repo.findUnclaimedLuggage(flightNo);
	        List<Luggage> missingLuggage = repo.findMissingLuggage(flightNo);
	        
	        Map<String, List<Luggage>> results = new HashMap<>();
	        results.put("unclaimed", unclaimedLuggage);
	        results.put("missing", missingLuggage);
	        
	        return ResponseEntity.ok().body(results);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().build();
	    }
	}
}
