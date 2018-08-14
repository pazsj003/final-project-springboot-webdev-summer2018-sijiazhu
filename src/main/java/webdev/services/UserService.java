package webdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.User;
import webdev.repositories.UserRepository;

@RestController
@CrossOrigin(origins="*",maxAge=3600, allowCredentials = "true")
public class UserService {
	@Autowired
	UserRepository repository;
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		repository.deleteById(id);
	}

	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser, HttpServletResponse response)
			throws Exception {

		Optional<User> data = repository.findById(userId);
		if (data.isPresent()) {
			User user = data.get();
			int checkStatus = set(user, newUser);
			if (checkStatus == 1) {
				repository.save(user);
				return user;
			} else if (checkStatus == -1) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				return null;
			} else if (checkStatus == 0) {
				throw new Exception("dont change anything");
			}
		}
		throw new Exception("dont find user");

	}

	@GetMapping("/api/profile")
	public User profile(HttpSession session,HttpServletResponse response) throws Exception {
		
		
		
		User currentUser = (User) session.getAttribute("currentuser");
		session.setMaxInactiveInterval(1800);
		if(currentUser==null) {
			System.err.println("profile not find  user " ); 
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
			
			
		}
      System.err.println("profile find  user " + currentUser.getUsername());
		return currentUser;

	}

	@PutMapping("/api/profile")
	public User updateProfile(@RequestBody User user, HttpSession session, HttpServletResponse response)
			throws Exception {

		User currentUser = (User) session.getAttribute("currentuser");
		
		if (currentUser != null) {

			User data = (User) findUserById(currentUser.getId(),response);
			if (data != null) {
				int checkStatus = set(data, user);
				if (checkStatus == 1) {
					session.setAttribute("currentuser", data);
					repository.save(data);
					return data;
				} else if (checkStatus == -1) {
					// same user name cant update
					response.setStatus(HttpServletResponse.SC_CONFLICT);

				} else
					return null;

			}

			else {
				throw new Exception("cant updateuser");
			}

		}
		throw new Exception("current profile is invalid,user not logged in");

	}

	public int set(User CurrentUser, User updateedUser) throws Exception {
		int change = 0;
		if (!CurrentUser.getUsername().equals(updateedUser.getUsername())) {
			// match is there same username used

			ArrayList<User> matchUser = (ArrayList<User>) findUserByUsername(updateedUser.getUsername());
			for (User finduser : matchUser) {

				if (finduser != null) {
					return -1;
				}
			}
			CurrentUser.setUsername(updateedUser.getUsername());
			change = 1;
		}
		if (CurrentUser.getRole() != updateedUser.getRole()) {
			CurrentUser.setRole(updateedUser.getRole());
			change = 1;
		}
		if (CurrentUser.getPhone() != updateedUser.getPhone()) {
			CurrentUser.setPhone(updateedUser.getPhone());
			change = 1;
		}
		if (CurrentUser.getEmail() != updateedUser.getEmail()) {
			CurrentUser.setEmail(updateedUser.getEmail());
			change = 1;
		}
		if (CurrentUser.getAddress() != updateedUser.getAddress()) {
			CurrentUser.setAddress(updateedUser.getAddress());
			change = 1;
		}
		if (CurrentUser.getFirstName() != updateedUser.getFirstName()) {
			CurrentUser.setFirstName(updateedUser.getFirstName());
			change = 1;
		}
		if (CurrentUser.getLastName() != updateedUser.getLastName()) {
			CurrentUser.setLastName(updateedUser.getLastName());
			change = 1;
		}
		if (CurrentUser.getIntro() != updateedUser.getIntro()) {
			CurrentUser.setIntro(updateedUser.getIntro());
			change = 1;
		}
		if (CurrentUser.getProfileimg() != updateedUser.getProfileimg()) {
			CurrentUser.setProfileimg(updateedUser.getProfileimg());
			change = 1;
		}
		if (CurrentUser.getPosts() != updateedUser.getPosts()) {
			CurrentUser.setPosts(updateedUser.getPosts());
			change = 1;
		}

		if (CurrentUser.getDateOfBirth() != updateedUser.getDateOfBirth()) {
			System.err.println("date of birth " + updateedUser.getDateOfBirth());
			CurrentUser.setDateOfBirth(updateedUser.getDateOfBirth());
			change = 1;
		}

		return change;
	}

	@PostMapping("/api/register")

	public User register(@RequestBody User user, HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {

		ArrayList<User> newUser = (ArrayList<User>) findUserByUsername(user.getUsername());
		for (User finduser : newUser) {
			if (finduser != null) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				return null;
			}
		}
		createUser(user, response);
		
		
		session.setAttribute("currentuser", user);

		return (User) session.getAttribute("currentuser");

	}

	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int userId,HttpServletResponse response) {
		Optional<User> data = repository.findById(userId);
		if (data.isPresent()) {
			return data.get();
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		return null;
	}

	@GetMapping("/api/user/")
	public Iterable<User> findUserByUsername(@RequestParam(name = "username", required = false) String username) {

		if (username != null) {
			return repository.findUserByUsername(username);
		}
		return repository.findAll();

	}
	
	@PostMapping("/api/searchuser")
	public Iterable<User> SearchUserByUsername(@RequestBody User user, HttpServletResponse response) {
		String username=user.getUsername();
		ArrayList<User> currentUser = new ArrayList<User>();
		if (username != null) {
			currentUser= (ArrayList<User>) repository.findUserByUsername(username);
		}

		
		if (currentUser != null) {
			
			for (User selectuser : currentUser) {
				if (selectuser != null) {
					System.err.println("user find " + selectuser.getUsername());
					return currentUser;
				}
			}
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
	

	@PostMapping("/api/user")
	public User createUser(@RequestBody User user, HttpServletResponse response) throws Exception {
		if (user.getUsername() != "" && user.getPassword() != "" && user.getFirstName() != ""
				&& user.getLastName() != "") {
			ArrayList<User> newUser = (ArrayList<User>) findUserByUsername(user.getUsername());
			for (User finduser : newUser) {
				if (finduser != null) {
					response.setStatus(HttpServletResponse.SC_CONFLICT);
					return null;
					 
				}
			}
			
			return repository.save(user);
		}
		System.err.print("User dont have enough information to create account");
		response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		return null;
		
	}

	@PostMapping("/api/login")
	public User login(@RequestBody User user, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ArrayList<User> currentUser = (ArrayList<User>) findUserByCredentials(user.getUsername(), user.getPassword());
		if (currentUser != null) {
			for (User selectuser : currentUser) {
				if (selectuser != null) {
					
//					HttpSession localSession = request.getSession();
//					localSession.setAttribute("currentuser", user);
					
					session.setAttribute("currentuser", selectuser);
					session.setAttribute("test",selectuser.getUsername());
					System.err.println("user login " + selectuser.getUsername());
					
					User sessionUser =  (User)session.getAttribute("currentuser");
					System.err.println("user login session " +sessionUser.getPassword());
					return (User) session.getAttribute("currentuser");
//					return selectuser;
				}

			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
//		throw new Exception("cant Login");
	}

	public Iterable<User> findUserByCredentials(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password) throws Exception {
		if (username != null && password != null) {
			return repository.findUserByCredentials(username, password);
		} else if (username != null) {
			throw new Exception("cant Login");
		}
		return repository.findAll();
	}

	@PostMapping("/api/logout")
	public User logout(HttpSession session) {
		session.invalidate();
		return null;
	}

	@GetMapping("/api/user")
	public List<User> findAllUser() {
		return (List<User>) repository.findAll();
	}
}
