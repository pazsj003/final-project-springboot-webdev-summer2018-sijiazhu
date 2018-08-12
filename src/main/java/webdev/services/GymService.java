package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import webdev.repositories.GymRepository;

@RestController
@CrossOrigin(origins="*",maxAge=3600, allowCredentials = "true")

public class GymService {
	
	@Autowired 
	GymRepository gymRepo;
	
	

	
	
}
