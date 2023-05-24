package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.edu.utem.ftmk.dad.luggagetracking.repo.PassengerFlightRepository;

@Controller
public class HomeViewController {

	@Autowired
	public final PassengerFlightRepository repo;
	
	public HomeViewController(PassengerFlightRepository repo)
	{
		this.repo = repo;
	}
	
	@GetMapping("/home")
	public String index()
	{
		return "home/index";
	}
	
	@PostMapping("/home/submitForm")
	public String getLuggagesByPassengerNationalityAndFlightNo(@RequestParam("identityNo") String identityNo, @RequestParam("flightNo") String flightNo, RedirectAttributes redirectAttributes)
	{
		// Validate form data
        if (identityNo.isEmpty() || flightNo.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill in all fields.");
            return "redirect:/home";
        }
        
        var passengerFlight = repo.findPassengerFlightByIdentityNoAndFlightNo(identityNo, flightNo);
        
        if(passengerFlight == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", "Invalid flight or passenger information.");
            return "redirect:/home";
        }
		
		return "redirect:/luggage/" + passengerFlight.getId().toString();
	}
}
