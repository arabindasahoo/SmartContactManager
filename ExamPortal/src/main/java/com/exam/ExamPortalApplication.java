package com.exam;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;

@SpringBootApplication
public class ExamPortalApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ExamPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//createUser();
	}

	private void createUser() throws Exception {
		User user = new User();
		user.setUsername("Arabinda13");
		user.setFirstName("Arabinda");
		user.setLastName("Sahoo");
		user.setEmail("arabinda@gmail.com");
		user.setPhone("9658002408");
		user.setProfile("default.png");
		user.setPassword(this.bCryptPasswordEncoder.encode("12345"));
		
		
		Role role = new Role();
		role.setId(40L);
		role.setRoleName("ADMIN");
		
		
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		
		Set<UserRole> userRolesSet = new HashSet<>();
		
		userRolesSet.add(userRole);
		User createUser = userService.createUser(user, userRolesSet);	
		
		System.out.println(createUser.getUsername());
	}

}
