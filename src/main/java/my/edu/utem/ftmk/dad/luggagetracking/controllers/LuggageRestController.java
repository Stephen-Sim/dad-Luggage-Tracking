package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import java.util.ArrayList;
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
	
	
	/**
	 * Retrieves the list of luggages associated with a passenger flight ID.
	 * 
	 * @param passengerFlightId
	 * @return
	 */
	@GetMapping("passengerFlight/{passengerFlightId}")
	public List<Luggage> getLuggagesByPassengerFlightId
		(@PathVariable long passengerFlightId)
	{
		return repo.findByPassengerFlightId(passengerFlightId);
	}
	
	/**
	 * Retrieves a luggage by its ID.
	 * 
	 * @param luggageId
	 * @return
	 */
	@GetMapping("{luggageId}")
	public Luggage getLuggagesById(@PathVariable long luggageId)
	{
		return repo.findById(luggageId).get();
	}
	
	/**
	 * Retrieves a luggage by its RFID.
	 * 
	 * @param rfid
	 * @return
	 */
	@GetMapping("getLuggageByRFID")
	public Luggage getLuggageByRFID(String rfid)
	{
		return repo.findByRFID(rfid);
	}
	
	/**
	 * Claims a luggage by updating its status and creating a log entry.
	 * 
	 * @param id
	 * @return
	 */
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
			
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					 .body("Fail to claim");
		}
    }
	
	/**
	 * Reports a luggage as missing by updating its status and creating a log entry.
	 * 
	 * @param id
	 * @return
	 */
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
			
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					 .body("Fail to claim");
		}
    }
	
	/**
	 * Searches for missing and unclaimed luggage based on the flight number.
	 * 
	 * @param flightNo
	 * @return
	 */
	@GetMapping("/missingAndUnclaim")
	public ResponseEntity<Map<String, List<Luggage>>> searchLuggage
		(@RequestParam("flightNo") String flightNo) {
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
	
	/**
	 * Retrieves the tracking information for luggage based on the flight number.
	 * 
	 * @param flightNo
	 * @return
	 */
	@GetMapping("/tracking")
	public ResponseEntity<Map<String, List<Map<String, Object>>>> getLuggageCheckPointTimeByFlightNo
		(String flightNo) {
	    List<Object[]> logTracking = repo
	    		.findLugageCheckPointTimeByFlightNo(flightNo);
	    
	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] result : logTracking) {
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("RFID", result[0]);
	        resultMap.put("cp1", result[1]);
	        resultMap.put("cp2", result[2]);
	        resultMap.put("cp3", result[3]);
	        resultMap.put("cp4", result[4]);
	        resultMap.put("status", result[5]);
	        responseList.add(resultMap);
	    }
	    
	    List<Object[]> mishandledLuggage = repo.findMishandledLuggage(flightNo);
	    
	    Map<String, List<Map<String, Object>>> results = new HashMap<>();
	    results.put("tracking", responseList);
	    
	    List<Map<String, Object>> mishandledList = new ArrayList<>();
	    
	    for (Object[] result : mishandledLuggage) {
	        Map<String, Object> mishandledMap = new HashMap<>();
	        mishandledMap.put("RFID", result[0]);
	        mishandledMap.put("DateTime", result[1]);
	        mishandledMap.put("message", "The luggage is mishandled at " 
	        		+ result[3] + " (" + result[2] + ").");
	        mishandledList.add(mishandledMap);
	    }
	    
	    results.put("mishandled", mishandledList);

	    return ResponseEntity.ok(results);
	}
}
