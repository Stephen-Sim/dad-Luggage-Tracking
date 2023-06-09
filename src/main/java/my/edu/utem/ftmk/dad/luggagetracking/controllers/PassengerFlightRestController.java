package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.luggagetracking.models.PassengerFlight;
import my.edu.utem.ftmk.dad.luggagetracking.repo.PassengerFlightRepository;

/**
 * This class represents a Spring MVC controller that handles the API
 * endpoints for passenger flights.
 * 
 * @author simsh
 *
 */
@RestController
@RequestMapping("api/passengerFlight")
public class PassengerFlightRestController {

	@Autowired
	public final PassengerFlightRepository  repo;
	
	public PassengerFlightRestController(PassengerFlightRepository repo)
	{
		this.repo = repo;
	}
	
	/**
	 *  Method : GET
	 * Retrieves the tracking information for luggage based on the 
	 * flight number.
	 * 
	 * @param identityNo
	 * @param flightNo
	 * @return
	 */
	@GetMapping("/getPassengerFlight")
	public PassengerFlight getPassengerFlightByIdentityNoAndFlightNo
		(String identityNo, String flightNo)
	{
		return repo.findPassengerFlightByIdentityNoAndFlightNo(identityNo, 
				flightNo);
	}
}
