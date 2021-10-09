package com.contact.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contact.manager.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
	@Query("select u from User as u where u.email = :Email")
	public User findByUserName(@Param("Email")String username);
}
