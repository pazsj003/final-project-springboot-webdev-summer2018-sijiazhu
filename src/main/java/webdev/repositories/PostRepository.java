package webdev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.Gym;
import webdev.models.Post;
import webdev.models.User;



public interface PostRepository extends JpaRepository<Post,Integer>{
	
	@Query(value="SELECT * FROM post  WHERE gym_id='HGjEL7S21GkvzLZFl4P4BQ' ",nativeQuery = true)
	Iterable <Post> findPostByGymId(
			String gymId );
			
}
