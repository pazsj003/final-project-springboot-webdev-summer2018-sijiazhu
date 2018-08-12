package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Post;



public interface PostRepository extends CrudRepository<Post,Integer>{

}
