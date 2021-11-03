package com.exam.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.exceptions.UserFoundException;
import com.exam.exceptions.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user")
public class ExamPortalController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//CREATE USER API
	
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception
	{
		
		user.setProfile("default.png");
		//Encode user password by using BcryptPassword encoder
		
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		
		Role role = new Role();
		role.setId(41L);
		role.setRoleName("NORMAL");
		
		
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		
		Set<UserRole> userRolesSet = new HashSet<>();
		
		userRolesSet.add(userRole);
		
		User createUser = this.userService.createUser(user, userRolesSet);
		
		return createUser;
	}
	
	
	//GETTING USER BY USERNAME API
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username")String username)
	{
		return this.userService.getUser(username);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId")Long userId)
	{
		userService.deleteUser(userId);
	}

	@ExceptionHandler(UserFoundException.class)
	public ResponseEntity<?> exceptionhandler(UserFoundException ex)
	{
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
	}
	
}
