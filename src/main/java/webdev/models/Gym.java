package webdev.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

@Entity
public class Gym {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String GymId;
	private int userId;
	
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@ManyToOne
	@JsonIgnore
	private User member;

	@OneToMany(mappedBy="club")
    private List<Post> posts;
	

//	@ManyToMany(
//	cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
//	 @JoinTable
//	(name = "User_GYM", 
//	joinColumns = { @JoinColumn(name = "member_id")}, 
//	inverseJoinColumns = {@JoinColumn(name = "gym_id")})
//	 
//		    Set<User> members = new HashSet<>();
//	  
	
	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public String getGymId() {
		return GymId;
	}

	public void setGymId(String gymId) {
		GymId = gymId;
	}
	  public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public User getMember() {
			return member;
		}

		public void setMember(User member) {
			this.member = member;
		}

	
	  
}
