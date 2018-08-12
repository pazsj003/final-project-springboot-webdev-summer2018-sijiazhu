package webdev.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Friend;
import webdev.models.Post;
import webdev.models.User;
import webdev.repositories.FriendRepository;
import webdev.repositories.UserRepository;


@RestController
@CrossOrigin(origins="*",maxAge=3600, allowCredentials = "true")


public class FriendService  {
	
	
	@Autowired 
	FriendRepository friendRepo;
	
	@Autowired 
	UserRepository userRepo;
	
	@DeleteMapping("/api/friend/{Id}")
	public void deleteFriend(@PathVariable("Id") int id) {
		System.err.println("delete friend by id " + id);
		
		DeleteByFriendByUserId(id);
	}
	
	public void DeleteByFriendByUserId(@RequestParam(name = "userId", required = false)int userId){
		
		friendRepo.deleteByUserId(userId);
		
	}
	
	@GetMapping("/api/user/{Id}/friend")
	public List<User> findAllFriendsForUser(@PathVariable("Id") int id) {
		Optional<User> data =userRepo.findById(id);
		if(data.isPresent()) {
			User user = data.get();
			
			List<Friend> friendlist = new ArrayList<>();
			List<User> FriendUser = new ArrayList<>();		
			
			friendlist=	 user.getFriends();
			
			
			for(Friend friend :friendlist) {
				System.err.println("friend list before add " + friend.getUserId());
				Optional<User> userfind =userRepo.findById(friend.getUserId());
				if(userfind.isPresent()) {
					User useradd = userfind.get();
					System.err.println("find friend user after add " + useradd.getUsername());
					FriendUser.add(useradd);
					
				}
			}
			return FriendUser;
			
		}
		return null;		
	}
	
	
	
	@GetMapping("/api/friend/{Id}")
	public Friend findFriendById(@PathVariable("Id") int Id) {
		Optional<Friend> data = friendRepo.findById(Id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	
	@PostMapping("/api/user/{userId}/friend")
	public Friend addFriend(@PathVariable("userId") int userId,@RequestBody Friend newFriend) {
		
		System.err.println("add friend  " + newFriend.getUserId());
		
		Optional<User> data = userRepo.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			 
	
			newFriend.setFriend(user);

			return friendRepo.save(newFriend);
		}
		return null;
	}
	
	

}
