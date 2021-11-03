package com.exam.service;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.exam.model.User;
import com.exam.model.UserRole;

@Component
public interface UserService {

	//CREATING USER
	public User createUser(User user,Set<UserRole> userRoles) throws Exception;

	public User getUser(String username);

	public void deleteUser(Long userId);
	
}
