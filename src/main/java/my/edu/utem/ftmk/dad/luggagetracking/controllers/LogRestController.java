package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.luggagetracking.repo.LogRepository;

@RestController
@RequestMapping("api/log")
public class LogRestController {
	@Autowired
	private LogRepository repo;	
	
	public LogRestController(LogRepository repo)
	{
		this.repo = repo;
	}
}
