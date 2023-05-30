package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.luggagetracking.models.CheckPoint;
import my.edu.utem.ftmk.dad.luggagetracking.repo.CheckPointRepository;

@RestController
@RequestMapping("api/checkpoint")
public class CheckPointRestController {
	@Autowired
	private final CheckPointRepository repo;
	
	public CheckPointRestController(CheckPointRepository repo)
	{
		this.repo = repo;
	}
	
	@GetMapping("getCheckPointByFlightNoAndCheckPointTypeId")
	public CheckPoint getCheckPointByFlightNoAndCheckPointTypeId(String flightNo, long checkPointTypeId)
	{
		return repo.findByFlightNoAndcheckPoinTypetId(flightNo, checkPointTypeId);
	}
}
