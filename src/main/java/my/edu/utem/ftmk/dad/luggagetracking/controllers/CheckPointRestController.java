package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.luggagetracking.models.CheckPoint;
import my.edu.utem.ftmk.dad.luggagetracking.repo.CheckPointRepository;

/**
 * This class is a RESTController class
 * 
 * @author simsh
 *
 */

@RestController
@RequestMapping("api/checkpoint")
public class CheckPointRestController {
	
	@Autowired
	private final CheckPointRepository repo;
	
	/**
	 * Constructor
	 * 
	 * @param repo
	 */
	public CheckPointRestController(CheckPointRepository repo)
	{
		this.repo = repo;
	}
	
	
	/**
	 * This method gets check point based on the flight number and 
	 * check point type id.
	 * 
	 * Web method: GET
	 * 
	 * @param flightNo
	 * @param checkPointTypeId
	 * @return A check point object for the flight and type
	 */
	@GetMapping("getCheckPointByFlightNoAndCheckPointTypeId")
	public CheckPoint getCheckPointByFlightNoAndCheckPointTypeId(
			String flightNo, long checkPointTypeId)
	{
		return repo.findByFlightNoAndcheckPoinTypetId(flightNo, 
				checkPointTypeId);
	}
}
