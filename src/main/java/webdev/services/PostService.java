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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Gym;
import webdev.models.Post;

import webdev.models.User;
import webdev.repositories.GymRepository;
import webdev.repositories.PostRepository;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins="*",maxAge=3600, allowCredentials = "true")

public class PostService {
	
	
	@Autowired 
	PostRepository postRepo;
	@Autowired 
	UserRepository userRepo;
	
	@Autowired
	GymRepository gymRepo;
	@DeleteMapping("/api/post/{Id}")
	public void deletePost(@PathVariable("Id") int id) {
		postRepo.deleteById(id);
	}
	
	
	
	@GetMapping("/api/user/{Id}/post")
	public List<Post> findAllPostsForUser(@PathVariable("Id") int id) {
		Optional<User> data =userRepo.findById(id);
		System.err.println("outside inside find post for user " + data.toString());
		if(data.isPresent()) {
			
			User user = data.get();
			System.err.println("yes inside find post for user " + user.getPosts().toString());
			return user.getPosts();
			
		}
		return null;		
	}
	

	
	
	public Iterable<Gym> findByGymId(@RequestParam(name = "gymId", required = false)String gymId,
			@RequestParam(name = "userId", required = false)int userId
			
			){
		
		    return  gymRepo.findGymByGymId(gymId,userId);
	}
	
	
	
	public  Iterable<Post> findAllPostsForGymFromSql(@RequestParam(name = "gymId", required = false)String gymId) {
		System.err.println(" insde the sql find post with gymid" + gymId);
		return  postRepo.findPostByGymId(gymId);
	}
	
	
	@GetMapping("/api/gym/{Id}/post")
	public List<Post> findAllPostsForGym(@PathVariable("Id") String id,HttpServletResponse response) {
		System.err.println(" gymId input " + id);
		
//		ArrayList<Gym> currentGym = new ArrayList<Gym>();
//		
//		currentGym  = (ArrayList<Gym>) findByGymId(id);
//		
		ArrayList<Post> PostFind = new ArrayList<Post>();
		PostFind=(ArrayList<Post>) findAllPostsForGymFromSql (id) ;
		System.err.println(" test with post test "  + PostFind.get(0).getText() );	
			if(PostFind!=null) {
				for(Post post:PostFind) {
					System.err.println(" test with post test"  + post.getText() + "userId" + post.getPostuserId() );
				}
				return PostFind;
			}
		
		
		
//		if (currentGym != null) {
//			
//			for (Gym selectgym : currentGym) {
//				if (selectgym != null) {
//					System.err.println("gym find " + selectgym.getGymId());
//					Gym GymFind = selectgym;
//					return GymFind.getPosts();
//				}
//			}
//		}
		
//		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		System.err.println(" can not get gym " );
		return null;	
		
		 
	}
	
	
	@GetMapping("/api/post/{Id}")
	public Post findPostById(@PathVariable("Id") int Id) {
		Optional<Post> data = postRepo.findById(Id);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/gym/{gymId}/post")
	public Post createPostforGym(@PathVariable("gymId") String gymId,
			@RequestBody Post newPost 
			) {
		
		System.err.println(" new come new post  gym" + newPost.getGymId() );
		
		ArrayList<Gym> currentGym = new ArrayList<Gym>();
		
		currentGym  =(ArrayList<Gym>) findByGymId(gymId,newPost.getPostuserId());
//		Optional<Gym>	currentGym  = findByGymId(gymId);
			if (currentGym != null) {
			
			for (Gym selectgym : currentGym) {
				if (selectgym != null) {
					System.err.println("gym create " + selectgym.getGymId());
					Gym GymFind = selectgym;
					LocalDateTime date = LocalDateTime.now();
					
					long now = System.currentTimeMillis();
					
					System.err.println(" post create at date " + date.toString()  + "at long time "  + now );
					
					
					newPost.setDate(date.toString());
					
					newPost.setTimeOrder(now);
					
					newPost.setClub(GymFind);
					
					return postRepo.save(newPost);
					
				}
			}
		}
//		
//		if(currentGym.isPresent()) {
//			Gym gym = currentGym.get();
//			LocalDateTime date = LocalDateTime.now();
//			
//			long now = System.currentTimeMillis();
//			
//			System.err.println(" post create at date " + date.toString()  + "at long time "  + now );
//			
//			
//			newPost.setDate(date.toString());
//			newPost.setTimeOrder(now);
//			
//			newPost.setClub(gym);
//
//			return postRepo.save(newPost);
//		}
		
			
		return null;
	}
	
	
	@PostMapping("/api/user/{userId}/post")
	public Post createPostforUser(@PathVariable("userId") int userId,@RequestBody Post newPost) {
		
		System.err.println(" new come new post " + newPost.getPostuserId() );
		
		
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
