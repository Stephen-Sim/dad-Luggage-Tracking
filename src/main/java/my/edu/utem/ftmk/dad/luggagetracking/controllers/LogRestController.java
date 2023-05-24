package my.edu.utem.ftmk.dad.luggagetracking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.luggagetracking.models.Log;
import my.edu.utem.ftmk.dad.luggagetracking.repo.LogRepository;
import my.edu.utem.ftmk.dad.luggagetracking.services.LuggageService;

@RestController
@RequestMapping("api/log")
public class LogRestController {
	@Autowired
	private final LogRepository repo;
	private final LuggageService luggageService;
	
	public LogRestController(LogRepository repo, LuggageService luggageService)
	{
		this.repo = repo;
		this.luggageService = luggageService;
	}
	
	@GetMapping("{luggageId}")
	public List<Log> getLogsByLuggageId(@PathVariable long luggageId)
	{
		return repo.findByLuggageId(luggageId);
	}
	
	@PostMapping("/storeLog")
	public ResponseEntity<HttpStatus> storeLog(Log log)
	{
		try
		{			
			repo.save(log);
			luggageService.updateLuggageStatus(log);
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception err)
		{
			System.out.println(err.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
