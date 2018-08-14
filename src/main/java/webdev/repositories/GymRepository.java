package webdev.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.Gym;


public interface GymRepository extends CrudRepository<Gym,Integer>{
	@Modifying
	@Transactional
	@Query("DELETE  FROM Gym  WHERE gym_id=:gymId AND user_id=:userId ")
	void deleteBygymId(@Param("gymId") String gymId,@Param("userId") int userId);
	
	@Query("SELECT g FROM Gym g WHERE g.GymId=:gymId AND user_id=:userId ")
	Iterable <Gym> findGymByGymId(
    		@Param("gymId") String gymId ,@Param("userId") int userId);
}
