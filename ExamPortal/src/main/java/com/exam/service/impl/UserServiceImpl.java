package com.exam.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.exceptions.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

import net.bytebuddy.asm.Advice.Return;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {

		User local = this.userRepo.findByUsername(user.getUsername());

		if (local != null) {
			System.out.println("User is already there!!");
			throw new UserFoundException("User is already present");
		} else {
			// CREATE USER
			for (UserRole userRole : userRoles) {

				roleRepo.save(userRole.getRole());

			}

			user.getUserRoles().addAll(userRoles);
			local = userRepo.save(user);

		}
		
		return local;
	}

	
	//Getting User By Username
	@Override
	public User getUser(String username) {
		return userRepo.findByUsername(username);
	}


	@Override
	public void deleteUser(Long userId) {
		
		userRepo.deleteById(userId);
		
	}
}
