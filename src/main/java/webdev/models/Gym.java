package webdev.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Gym {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	

	@ManyToMany(cascade = { CascadeType.ALL })
	  @JoinTable
		    Set<User> members = new HashSet<>();
	  
	  public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Set<User> getMembers() {
			return members;
		}

		public void setMembers(Set<User> members) {
			this.members = members;
		}
	  
}
