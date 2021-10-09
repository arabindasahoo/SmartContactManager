package com.contact.manager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contact.manager.entity.Contact;
import com.contact.manager.entity.User;
import com.contact.manager.repository.ContactRepository;
import com.contact.manager.repository.UserRepository;

@RestController
public class SearchController {

	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private ContactRepository contactRepo;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal)
	{
		User user = UserRepository.findByUserName(principal.getName());
		
		List<Contact> contacts = contactRepo.findByNameContainingAndUser(query, user);
		
		return ResponseEntity.ok(contacts);
		
	}
}
