package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.edu.utem.ftmk.dad.luggagetracking.models.PassengerFlight;

/**
 * This class represents a Spring MVC controller that handles the views 
 * and form submission for passenger flights.
 * 
 * @author simsh
 *
 */
@Controller
public class PassengerFlightViewController {

	private String url = "http://localhost:8080/trackingapp/api/passengerFlight";
	
	@GetMapping("/home")
	public String index()
	{
		return "home/index";
	}
	
	/**
	 *  checks the form input
	 * 
	 * @param identityNo
	 * @param flightNo
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/home/submitForm")
	public String getLuggagesByPassengerNationalityAndFlightNo
		(@RequestParam("identityNo") String identityNo, 
				@RequestParam("flightNo") String flightNo, 
				RedirectAttributes redirectAttributes)
	{
		// Validate form data
        if (identityNo.isEmpty() || flightNo.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", 
            		"Please fill in all fields.");
            return "redirect:/home";
        }
        
        String url = this.url + "/getPassengerFlight?identityNo=" 
        		+ identityNo + "&flightNo=" + flightNo;
		
		RestTemplate restTemplate = new RestTemplate();
		var passengerFlight = restTemplate
				.getForObject(url, PassengerFlight.class);
        
        if(passengerFlight == null)
        {
        	redirectAttributes
        		.addFlashAttribute("errorMessage", 
        				"Invalid flight or passenger information.");
            return "redirect:/home";
        }
		
		return "redirect:/luggage/" + passengerFlight.getId().toString();
	}
}
