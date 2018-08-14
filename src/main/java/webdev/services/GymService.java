package webdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

 
import webdev.models.Gym;
import webdev.models.User;
import webdev.repositories.GymRepository;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins="*",maxAge=3600, allowCredentials = "true")

public class GymService {
	
	@Autowired 
	GymRepository gymRepo;
	
	@Autowired 
	UserRepository userRepo;
	
	@GetMapping("/api/gym/{Id}")
	public Gym findGymById(@PathVariable("Id") int Id) {
		Optional<Gym> data = gymRepo.findById(Id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@DeleteMapping("/api/gym/{Id}")
	public void deleteGym(@PathVariable("Id") String id,@RequestBody User user) {
		System.err.println("delete gym by id " + id);
		
		DeleteByGymByGymId(id,user.getId());
	}
	
	public void DeleteByGymByGymId(
	@RequestParam(name = "gymId", required = false)String gymId,
	@RequestParam(name = "userId", required = false)int userId		
			
			){
		
		gymRepo.deleteBygymId(gymId,userId);
		
	}
	
	@GetMapping("/api/user/{Id}/gym")
	public List<Gym> findAllGymsForUser(@PathVariable("Id") int id) {
		Optional<User> data =userRepo.findById(id);
		System.err.println("outside  find gym for user ");
		if(data.isPresent()) {
			User user = data.get();
			List<Gym> gymlist=user.getGyms();
			for(Gym gym:gymlist) {
				System.err.println("insde  find gym for user " + gym.getGymId());
			}
			
			return gymlist;	
		}
		return null;		
	}
	
	
	
	@PostMapping("/api/user/{userId}/gym")
	public Gym EnrollGym(@PathVariable("userId") int userId,@RequestBody Gym newGym) {
			
		System.err.println("add gym  " + newGym.getGymId());
		
		Optional<User> data = userRepo.findById(userId);
		
		if(data.isPresent()) {
			User user = data.get();
			 
	
			newGym.setMember(user);;

			return gymRepo.save(newGym);
		}
		return null;
	}
	
	
	
	
}
