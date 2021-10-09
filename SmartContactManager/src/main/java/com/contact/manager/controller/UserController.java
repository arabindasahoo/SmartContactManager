package com.contact.manager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.manager.entity.Contact;
import com.contact.manager.entity.User;
import com.contact.manager.helper.Message;
import com.contact.manager.repository.ContactRepository;
import com.contact.manager.repository.UserRepository;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/index")
	public String login(Model model) {

		model.addAttribute("title", "User DashBoard - Contact manager");
		return "/normal/user-dashboard";
	}

	/*
	 * open Add Contact form
	 * 
	 */
	@GetMapping("/addContact")
	public String addContact(Model model) {
		model.addAttribute("title", "Add Contact - Contact manager");
		model.addAttribute("contact", new Contact());
		return "/normal/addContact";
	}

	@PostMapping("/processContact")
	public String processContact(@ModelAttribute("contact") Contact contact, Model model, Principal principal,
			@RequestParam("profileImage") MultipartFile multipartFile, HttpSession session) {

		try {

			String name = principal.getName();
			User user = userRepo.findByUserName(name);

			if (multipartFile.isEmpty()) {

				contact.setImageUrl("contact.png");

			} else {
				contact.setImageUrl(multipartFile.getOriginalFilename());

				File file = new ClassPathResource("/static/img/").getFile();

				Path path = Paths.get(file.getAbsolutePath() + File.separator + multipartFile.getOriginalFilename());

				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			contact.setUser(user);
			user.getContacts().add(contact);

			userRepo.save(user);

			session.setAttribute("message", new Message("Contact added successfully", "success"));

			model.addAttribute("title", "Add Contact - Contact manager");
			model.addAttribute("contact", new Contact());

		} catch (Exception e) {

			e.printStackTrace();
			session.setAttribute("message", new Message("Error in adding contact", "danger"));

		}

		return "/normal/addContact";
	}

	@GetMapping("/viewContacts/{page}")
	public String viewContact(@PathVariable("page") Integer page, Principal principal, Model model) {

		String name = principal.getName();
		User user = userRepo.findByUserName(name);

		PageRequest of = PageRequest.of(page, 3);

		Page<Contact> contacts = contactRepo.findContactsByUser(user.getId(), of);

		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		model.addAttribute("title", "View Contact - Contact manager");

		return "/normal/viewContact";
	}

	@GetMapping("/contact/{cId}")
	public String getContactDetails(@PathVariable("cId") Integer cId, Model model, Principal principal,
			HttpSession session) {
		Contact contact = null;
		try {

			contact = contactRepo.findById(cId).get();

			String name = principal.getName();
			User user = userRepo.findByUserName(name);

			if (user.getId() == contact.getUser().getId()) {
				model.addAttribute("contact", contact);
				model.addAttribute("title", "Contact Profile - Contact Manager");
			} else {

				model.addAttribute("contact", new Contact());
				model.addAttribute("title", "Contact Profile - Contact Manager");
			}

		} catch (Exception e) {
			model.addAttribute("contact", new Contact());
			model.addAttribute("title", "Contact Profile - Contact Manager");
			return "/normal/contactProfile";
		}

		return "/normal/contactProfile";
	}

	@RequestMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, Model model, Principal principal,
			HttpSession session) {

		String name = principal.getName();
		User user = userRepo.findByUserName(name);
		
		Contact contact = contactRepo.findById(cId).get();
		
		if(user.getId() == contact.getUser().getId())
		{
			contact.setUser(null);
			
			//before Delete Contact
			//delete Image
			
			contactRepo.deleteById(cId);;
		}
		
		session.setAttribute("message", new Message("Contact Successfully deleted", "success"));
		return "redirect:/user/viewContacts/0";
	}
	
	
	//Open Update Form
	
	@PostMapping("/updateContact/{cId}")
	public String updateForm(@PathVariable("cId")Integer cId,Model model)
	{
		
		Contact contact = contactRepo.findById(cId).get();
		
		
		model.addAttribute("contact", contact);

		model.addAttribute("title", "Update Contact - Contact Manager");
		return "/normal/updateContact";
	}
	
	@PostMapping("/processUpdateContact")
	public String processUpdateContact(@ModelAttribute("contact")Contact contact,
			Model model,HttpSession session,@RequestParam("profileImage")MultipartFile multipartFile,Principal principal)
	{
		
		Contact oldContact = contactRepo.findById(contact.getcId()).get();
		try {
			
			if(!multipartFile.isEmpty())
			{
				
				//Delete Old photo and then update
				
				File deleteFile = new ClassPathResource("/static/img/").getFile();
				File file = new File(deleteFile, oldContact.getImageUrl());
				file.delete();
				
				//update Photo
				
				contact.setImageUrl(multipartFile.getOriginalFilename());
				File saveFile = new ClassPathResource("/static/img/").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + multipartFile.getOriginalFilename());

				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			else {
				contact.setImageUrl(oldContact.getImageUrl());
			}
		User user = userRepo.findByUserName(principal.getName());
		contact.setUser(user);
		contactRepo.save(contact);
		session.setAttribute("message", new Message("Contact Updated Successfully", "success"));	
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return "redirect:/user/viewContacts/0";
	}
	
	@GetMapping("/profile/{userId}")
	public String profile(@PathVariable("userId")Integer userId,Model model,Principal principal)
	{
		
			
		String name = principal.getName();
		User savedUser = userRepo.findByUserName(name);
		
		if(savedUser.getId() == userId)
		{
			User user = userRepo.findById(userId).get();	
			model.addAttribute("user", user);
			model.addAttribute("title", "Your Profile - Contact Manager");
		}
		else {
			model.addAttribute("user", new User());
			return "/normal/userProfile";
		}
		return "/normal/userProfile";
	}
	
	@GetMapping("/settings")
	public String settings(Model model)
	{
		model.addAttribute("title", "Settings - Contact Manager");
		return "/normal/settings";
	}
	
	//change Password Handler
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("newPassword")String newPassword,
									@RequestParam("oldPassword")String oldPassword,
									Principal principal,HttpSession session)
	{
		
		User user = userRepo.findByUserName(principal.getName());
		
		String currentPassword = user.getPassword();
		
		if(passwordEncoder.matches(oldPassword, currentPassword))
		{
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepo.save(user);
			
			session.setAttribute("message", new Message("Password Changed Successfully", "success"));
			
		}
		
		else {
			
			session.setAttribute("message", new Message("Enter Correct Old Password", "danger"));
			return "redirect:/user/settings";
		}
		
		return "redirect:/user/settings";
	}
	
	
	
	
	@ModelAttribute
	public void commonData(Principal principal,Model model) {
		
		String name = principal.getName();
		User user = userRepo.findByUserName(name);
		model.addAttribute("user", user);
		
		
	}

}
