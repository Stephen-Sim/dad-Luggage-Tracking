package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;

@Controller
public class LuggageViewController {
	
	private final String url = "http://localhost:8080/trackingapp/api/luggage/";
	
	@GetMapping("/luggage/{passengerFligthId}")
	public String index(@PathVariable Integer passengerFligthId, Model model)
	{
		// The URI for GET luggage
		String uri = this.url + "passengerFlight/" + passengerFligthId.toString();
		
		// Get a list order types from the web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Luggage[]> response = restTemplate.getForEntity(uri, Luggage[].class);
		
		// Parse JSON data to array of object
		Luggage luggages[] = response.getBody();

		List<Luggage> luggagesList = Arrays.asList(luggages);
		
		// Attach list to model as attribute
		model.addAttribute("luggages", luggagesList);
		
		return "luggage/index";
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	@GetMapping("luggage/tracking")
	public String indexTracking(){
		return "luggage/tracking";
	}
}
