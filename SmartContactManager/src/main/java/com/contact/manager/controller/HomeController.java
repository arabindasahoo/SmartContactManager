package com.contact.manager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.manager.entity.User;
import com.contact.manager.helper.Message;
import com.contact.manager.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String getHome(Model model,Principal principal)
	{
		
		if(principal == null)
		{
			model.addAttribute("title", "Home - Contact manager");
			return "home";
		}
		else {
			User user = userRepository.findByUserName(principal.getName());
			model.addAttribute("title", "User Dashboard - Contact manager");
			model.addAttribute("user", user);
			return "/normal/user-dashboard";
		}
		
	}
	

	@GetMapping("/about")
	public String getAbout(Model model)
	{
		model.addAttribute("title", "About - Contact manager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title", "Register - Contact manager");
		model.addAttribute("user", new User());
		
		return "signup";
	}
	
	@GetMapping("/signin")
	public String login(Model model)
	{
		model.addAttribute("title", "Login - Contact manager");
		return "signin";
	}
	
	@GetMapping("/forgotPassword")
	public String forgotPassword(Model model)
	{
		model.addAttribute("title", "Forgot Password - Contact Manager");
		return "forgot-password-form";
	}
	
	
	
	
	@PostMapping("/doRegister")
	public String register(@Valid@ModelAttribute("user")User user,BindingResult result,
				@RequestParam(value = "agreement",defaultValue = "false")boolean agreement,
				Model model,HttpSession session,@RequestParam("profileImage")MultipartFile multipartFile)
	{
		
		try {
			
		
			if(!agreement)
			{
				System.out.println("You have not accepted the terms and conditions");
				throw new RuntimeException("You have not accepted the terms and conditions");
			}
			if(result.hasErrors())
			{
				System.out.println(result.toString());
				model.addAttribute("user",user);
				model.addAttribute("title", "Register - Contact manager");
				return "signup";
			}
			if(!multipartFile.isEmpty())
			{
				user.setImageUrl(multipartFile.getOriginalFilename());
				File file = new ClassPathResource("/static/img/").getFile();
				Path path = Paths.get(file.getAbsolutePath() + File.separator + multipartFile.getOriginalFilename());
				Files.copy(multipartFile.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
			}
			
			
			user.setRole("ROLE_USER");
			user.setEnable(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			User savedUser = userRepository.save(user);
			System.out.println("User "+user);
			model.addAttribute("user",new User());
			session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
			return "signup";
		} catch (Exception e) {
			
			e.printStackTrace();
			session.setAttribute("message", new Message("You have not accepted the terms and conditions", "alert-danger"));
			model.addAttribute("user",user);
			model.addAttribute("title", "Register - Contact manager");
			return "signup";
		}
		
		
		
	}
	

}
