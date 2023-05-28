package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LuggageRepository;

@RestController
@RequestMapping("api/luggage")
public class LuggageRestController {

	@Autowired
	private final LuggageRepository repo;
	
	public LuggageRestController(LuggageRepository repo)
	{
		this.repo = repo;
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
}
