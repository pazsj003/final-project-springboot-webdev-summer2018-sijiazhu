package webdev.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import webdev.models.Post;

import webdev.models.User;
import webdev.repositories.PostRepository;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins="*",maxAge=3600, allowCredentials = "true")

public class PostService {
	
	
	@Autowired 
	PostRepository postRepo;
	@Autowired 
	UserRepository userRepo;
	@DeleteMapping("/api/post/{Id}")
	public void deletePost(@PathVariable("Id") int id) {
		postRepo.deleteById(id);
	}
	
	
	@GetMapping("/api/user/{Id}/post")
	public List<Post> findAllPostsForUser(@PathVariable("Id") int id) {
		Optional<User> data =userRepo.findById(id);
		if(data.isPresent()) {
			User user = data.get();
			return user.getPosts();
			
		}
		return null;		
	}
	
	
	@GetMapping("/api/post/{Id}")
	public Post findUserById(@PathVariable("Id") int Id) {
		Optional<Post> data = postRepo.findById(Id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	
	@PostMapping("/api/user/{userId}/post")
	public Post createPost(@PathVariable("userId") int userId,@RequestBody Post newPost) {
		
		
		
		Optional<User> data = userRepo.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			LocalDateTime date = LocalDateTime.now();
			
			long now = System.currentTimeMillis();
			
			System.err.println(" post create at date " + date.toString()  + "at long time "  + now );
			
			
			newPost.setDate(date.toString());
			newPost.setTimeOrder(now);
			
			newPost.setUser(user);

			return postRepo.save(newPost);
		}
		return null;
	}
	
	
	@GetMapping("/api/post")
	public Iterable<Post> findAllPost() {
		return postRepo.findAll(); 
	}
	
	
	
	
}
