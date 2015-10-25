package io.pivotal.payeezy;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
  
	public List<User> findByEmail(String email);
  
	public List<User> findById(Long id);
	
	//public User save(User user);
	

}