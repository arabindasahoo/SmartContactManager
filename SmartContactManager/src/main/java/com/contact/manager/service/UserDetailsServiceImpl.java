package com.contact.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.contact.manager.entity.User;
import com.contact.manager.exception.UserNotFoundException;
import com.contact.manager.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUserName(username);
		if(user == null)
		{
			throw new UsernameNotFoundException("NO user present with this username "+username);
		}
		return new UserDetailsImpl(user);
	}

}
