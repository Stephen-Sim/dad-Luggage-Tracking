package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.edu.utem.ftmk.dad.luggagetracking.models.CheckPoint;
import my.edu.utem.ftmk.dad.luggagetracking.models.Log;
import my.edu.utem.ftmk.dad.luggagetracking.models.Luggage;
import my.edu.utem.ftmk.dad.luggagetracking.models.StatusType;

@Controller
public class CheckPointViewController {

	private final String checkPointUrl = "http://localhost:8080/trackingapp/api/checkpoint/";
	private final String luggageUrl = "http://localhost:8080/trackingapp/api/luggage/";
	private final String logUrl = "http://localhost:8080/trackingapp/api/log/";
	
	@GetMapping("/checkpoint/truck")
	public String indexTruck()
	{
		return "checkpoints/truckCheckPoint";
	}
	
	@PostMapping("/checkpoint/truck/record")
	public String truckRecord(@RequestParam("checkPointName") String checkPointName, @RequestParam("rfid") String rfid, RedirectAttributes redirectAttributes)
	{
		if (checkPointName.isEmpty() || checkPointName.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill in all fields.");
            return "redirect:/checkpoint/truck";
        }
		
		var checkPoint = getCheckPointByName(checkPointName);
		
		if(checkPoint == null || checkPoint.getCheckPointType().getId() != 2)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", "Invalid Check Point Name");
            return "redirect:/checkpoint/truck";
        }
		
		var luggage = getLuggageByRFID(rfid);
		
		if(luggage == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", "Invalid Luggage RFID");
            return "redirect:/checkpoint/truck";
        }
		
		StatusType status = new StatusType();
		status.setId(2L);
		
		Log log = new Log();
		log.setDateTime(LocalDateTime.now());
		log.setCheckPoint(checkPoint);
		log.setLuggage(luggage);
		log.setStatus(status);
		
		String url = this.logUrl + "/storeLog";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<HttpStatus> reponse = restTemplate.postForEntity(url, log, HttpStatus.class);
		
		if (reponse.getStatusCode() != HttpStatus.OK)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Log failed to store. Please try again.");
            return "redirect:/checkpoint/truck";
		}
		
		redirectAttributes.addFlashAttribute("message", "Log recorded successfully");
        return "redirect:/checkpoint/truck";
	}
	
	@GetMapping("/checkpoint/handlinghub")
	public String indexHandlingHub()
	{
		return "checkpoints/handlingHubCheckPoint";
	}
	
	@PostMapping("/checkpoint/handlinghub/record")
	public String handlingHubRecord(@RequestParam("checkPointName") String checkPointName, @RequestParam("rfid") String rfid, RedirectAttributes redirectAttributes)
	{
		if (checkPointName.isEmpty() || checkPointName.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill in all fields.");
            return "redirect:/checkpoint/handlinghub";
        }
		
		var checkPoint = getCheckPointByName(checkPointName);
		
		if(checkPoint == null || checkPoint.getCheckPointType().getId() != 3)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", "Invalid Check Point Name");
            return "redirect:/checkpoint/handlinghub";
        }
		
		var luggage = getLuggageByRFID(rfid);
		
		if(luggage == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", "Invalid Luggage RFID");
            return "redirect:/checkpoint/handlinghub";
        }
		
		StatusType status = new StatusType();
		status.setId(3L);
		
		Log log = new Log();
		log.setDateTime(LocalDateTime.now());
		log.setCheckPoint(checkPoint);
		log.setLuggage(luggage);
		log.setStatus(status);
		
		String url = this.logUrl + "/storeLog";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<HttpStatus> reponse = restTemplate.postForEntity(url, log, HttpStatus.class);
		
		if (reponse.getStatusCode() != HttpStatus.OK)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Log failed to store. Please try again.");
            return "redirect:/checkpoint/handlinghub";
		}
		
		redirectAttributes.addFlashAttribute("message", "Log recorded successfully");
        return "redirect:/checkpoint/handlinghub";
	}
	
	@GetMapping("/checkpoint/claimbay")
	public String indexClaimBay()
	{
		return "checkpoints/claimbayCheckPoint";
	}
	
	@PostMapping("/checkpoint/claimbay/record")
	public String claimBayRecord(@RequestParam("checkPointName") String checkPointName, @RequestParam("rfid") String rfid, RedirectAttributes redirectAttributes)
	{
		if (checkPointName.isEmpty() || checkPointName.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill in all fields.");
            return "redirect:/checkpoint/claimbay";
        }
		
		var checkPoint = getCheckPointByName(checkPointName);
		
		if(checkPoint == null || checkPoint.getCheckPointType().getId() != 4)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", "Invalid Check Point Name");
            return "redirect:/checkpoint/claimbay";
        }
		
		var luggage = getLuggageByRFID(rfid);
		
		if(luggage == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", "Invalid Luggage RFID");
            return "redirect:/checkpoint/claimbay";
        }
		
		StatusType status = new StatusType();
		status.setId(4L);
		
		Log log = new Log();
		log.setDateTime(LocalDateTime.now());
		log.setCheckPoint(checkPoint);
		log.setLuggage(luggage);
		log.setStatus(status);
		
		String url = this.logUrl + "/storeLog";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<HttpStatus> reponse = restTemplate.postForEntity(url, log, HttpStatus.class);
		
		if (reponse.getStatusCode() != HttpStatus.OK)
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Log failed to store. Please try again.");
            return "redirect:/checkpoint/claimbay";
		}
		
		redirectAttributes.addFlashAttribute("message", "Log recorded successfully");
        return "redirect:/checkpoint/claimbay";
	}
	
	private CheckPoint getCheckPointByName(String name)
	{
		String url = checkPointUrl + "/getCheckPointByName?name=" + name;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CheckPoint> response = restTemplate.getForEntity(url, CheckPoint.class);
		
		CheckPoint checkPoint = response.getBody();
		
		return checkPoint;
	}
	
	private Luggage getLuggageByRFID(String rfid)
	{
		String url = luggageUrl + "/getLuggageByRFID?rfid=" + rfid;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Luggage> response = restTemplate.getForEntity(url, Luggage.class);
		
		Luggage luggage = response.getBody();
		
		return luggage;
	}
}
