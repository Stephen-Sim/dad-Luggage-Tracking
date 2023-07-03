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

/**
 * This class is a controller class that handles the views and actions related
 * to checkpoints in luggage tracking.
 * @author simsh
 *
 */
@Controller
public class CheckPointViewController {

	private final String checkPointUrl = "http://localhost:8080/trackingapp"
			+ "/api/checkpoint/";
	private final String luggageUrl = "http://localhost:8080/trackingapp"
			+ "/api/luggage/";
	private final String logUrl = "http://localhost:8080/trackingapp/api/log/";
	
	@GetMapping("/checkpoint/truck")
	public String indexTruck()
	{
		return "checkpoints/truckCheckPoint";
	}
	
	/**
	 * This method displays checkpoint/truck.html
	 * 
	 * This method also check the form input
	 * 
	 * @return A web page
	 */
	@PostMapping("/checkpoint/truck/record")
	public String truckRecord(@RequestParam("flightNo") String flightNo, 
			@RequestParam("rfid") String rfid, 
			RedirectAttributes redirectAttributes)
	{
		if (flightNo.isEmpty() || flightNo.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", 
            		"Please fill in all fields.");
            return "redirect:/checkpoint/truck";
        }
		
		var checkPoint = getCheckPointByFlightNo(flightNo, 2);
		
		if(checkPoint == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", 
        			"Invalid Flight No");
            return "redirect:/checkpoint/truck";
        }
		
		var luggage = getLuggageByRFID(rfid);
		
		if(luggage == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", 
        			"Invalid Luggage RFID");
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
		ResponseEntity<HttpStatus> reponse = restTemplate
				.postForEntity(url, log, HttpStatus.class);
		
		if (reponse.getStatusCode() != HttpStatus.OK)
		{
			redirectAttributes.addFlashAttribute("errorMessage", 
					"Log failed to store. Please try again.");
            return "redirect:/checkpoint/truck";
		}
		
		redirectAttributes.addFlashAttribute("message", 
				"Log recorded successfully");
        return "redirect:/checkpoint/truck";
	}
	
	
	/**
	 * This method displays handlingHubCheckPoint.html
	 * 
	 * @return A web page
	 */
	@GetMapping("/checkpoint/handlinghub")
	public String indexHandlingHub()
	{
		return "checkpoints/handlingHubCheckPoint";
	}
	
	/**
	 * This method checks the input of handlinghub
	 * 
	 * @return A web page
	 */
	@PostMapping("/checkpoint/handlinghub/record")
	public String handlingHubRecord(@RequestParam("flightNo") String flightNo, 
			@RequestParam("rfid") String rfid, 
			RedirectAttributes redirectAttributes)
	{
		
		
		// Validate parameters
		if (flightNo.isEmpty() || flightNo.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", 
            		"Please fill in all fields.");
            return "redirect:/checkpoint/handlinghub";
        }
		
		// Validate flight number
		var checkPoint = getCheckPointByFlightNo(flightNo, 3);
		
		if(checkPoint == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", 
        			"Invalid Flight No");
            return "redirect:/checkpoint/handlinghub";
        }
		
		// Validate flight lugage RFID
		var luggage = getLuggageByRFID(rfid);
		
		if(luggage == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", 
        			"Invalid Luggage RFID");
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
		ResponseEntity<HttpStatus> reponse = restTemplate.postForEntity(url, 
				log, HttpStatus.class);
		
		if (reponse.getStatusCode() != HttpStatus.OK)
		{
			redirectAttributes.addFlashAttribute("errorMessage", 
					"Log failed to store. Please try again.");
            return "redirect:/checkpoint/handlinghub";
		}
		
		redirectAttributes.addFlashAttribute("message", 
				"Log recorded successfully");
        return "redirect:/checkpoint/handlinghub";
	}
	
	/**
	 * This method displays checkpoint/claimbay.html
	 * 
	 * 
	 * @return A web page
	 */
	@GetMapping("/checkpoint/claimbay")
	public String indexClaimBay()
	{
		return "checkpoints/claimbayCheckPoint";
	}
	
	/**
	 * This method displays checkpoint/claimbay.html
	 * 
	 * This method also check the form input
	 * 
	 * @return A web page
	 */
	@PostMapping("/checkpoint/claimbay/record")
	public String claimBayRecord(@RequestParam("flightNo") String flightNo, 
			@RequestParam("rfid") String rfid, RedirectAttributes redirectAttributes)
	{
		if (flightNo.isEmpty() || flightNo.isEmpty()) {
            // Set error message
            redirectAttributes.addFlashAttribute("errorMessage", 
            		"Please fill in all fields.");
            return "redirect:/checkpoint/claimbay";
        }
		
		var checkPoint = getCheckPointByFlightNo(flightNo, 4);
		
		if(checkPoint == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", 
        			"Invalid Flight No");
            return "redirect:/checkpoint/claimbay";
        }
		
		var luggage = getLuggageByRFID(rfid);
		
		if(luggage == null)
        {
        	redirectAttributes.addFlashAttribute("errorMessage", 
        			"Invalid Luggage RFID");
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
		ResponseEntity<HttpStatus> reponse = restTemplate
				.postForEntity(url, log, HttpStatus.class);
		
		if (reponse.getStatusCode() != HttpStatus.OK)
		{
			redirectAttributes.addFlashAttribute("errorMessage", 
					"Log failed to store. Please try again.");
            return "redirect:/checkpoint/claimbay";
		}
		
		redirectAttributes.addFlashAttribute("message", 
				"Log recorded successfully");
        return "redirect:/checkpoint/claimbay";
	}
	
	/**
	 * this method retrieves CheckPoint by Flight No
	 * 
	 * @param flightNo
	 * @param checkPointTypeId
	 * @return
	 */
	private CheckPoint getCheckPointByFlightNo(String flightNo, long checkPointTypeId)
	{
		String url = checkPointUrl + 
				"/getCheckPointByFlightNoAndCheckPointTypeId?flightNo=" 
				+ flightNo + "&checkPointTypeId=" + checkPointTypeId;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CheckPoint> response = restTemplate
				.getForEntity(url, CheckPoint.class);
		
		CheckPoint checkPoint = response.getBody();
		
		return checkPoint;
	}
	
	/**
	 * this method retrieves Luggage by RFID
	 * 
	 * @param flightNo
	 * @param checkPointTypeId
	 * @return
	 */
	private Luggage getLuggageByRFID(String rfid)
	{
		String url = luggageUrl + "/getLuggageByRFID?rfid=" + rfid;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Luggage> response = restTemplate
				.getForEntity(url, Luggage.class);
		
		Luggage luggage = response.getBody();
		
		return luggage;
	}
	
	/**
	 * This method displays checkpoint/unclaimAndMissing.html
	 * 
	 * @return A web page
	 */
	@GetMapping("/checkpoint/unclaimAndMissing")
	private String indexUnclaimAndMissing()
	{
		return "checkpoints/unclaimAndMissing";
	}
}
