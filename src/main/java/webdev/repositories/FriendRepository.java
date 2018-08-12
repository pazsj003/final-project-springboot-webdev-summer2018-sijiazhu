package webdev.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.Friend;
import webdev.models.User;


public interface FriendRepository extends CrudRepository<Friend,Integer> {
	@Modifying
	@Transactional
	@Query("DELETE  FROM Friend  WHERE user_id=:userId ")
	void deleteByUserId(@Param("userId") int userId);
	
	
}
