package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.luggagetracking.models.Log;
import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;

@Controller
public class LogViewController {
	private final String url = "http://localhost:8080/trackingapp/api/log/";
	
	@GetMapping("/log/{luggageId}")
	public String index(@PathVariable Integer luggageId, Model model)
	{
		// The URI for GET log
		String uri = this.url + luggageId.toString();
		
		// Get a list order types from the web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Log[]> response = restTemplate.getForEntity(uri, Log[].class);
		
		// Parse JSON data to array of object
		Log logs[] = response.getBody();

		List<Log> logsList = Arrays.asList(logs);
		
		// Attach list to model as attribute
		model.addAttribute("logs", logsList);
		
		// The URI to GET luggage
		uri = "http://localhost:8080/trackingapp/api/luggage/" +  luggageId.toString();
		Luggage luggage = restTemplate.getForObject(uri, Luggage.class);
		
		// Attach luggage to model as attribute
		model.addAttribute("luggage", luggage);
		
		return "log/index";
	}
}