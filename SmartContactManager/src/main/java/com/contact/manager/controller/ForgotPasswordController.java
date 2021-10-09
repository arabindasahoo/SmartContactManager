package com.contact.manager.controller;

import java.util.Random;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.manager.entity.User;
import com.contact.manager.helper.Message;
import com.contact.manager.repository.UserRepository;

@Controller
public class ForgotPasswordController {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	Random random = new Random();
	
	@PostMapping("/sendOTP")
	public String sendOTP(@RequestParam("email")String email,Model model,HttpSession session)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(email);
		message.setSubject("Smart Contact Manager - OTP for Password Change");
		
		int otp = random.nextInt(999999);	
		
		message.setText("Your OTP for Reset Password is :"+otp);
		
		try {
			User user = userRepo.findByUserName(email);
			
			if(user.isEnable())
		
			javaMailSender.send(message);
			session.setAttribute("OTP", otp);
			session.setAttribute("email", email);
			session.setAttribute("message", new Message("OTP has been sent to your email..!!", "success"));
			model.addAttribute("title", "Verify OTP - Contact Manager");
			return "verify-otp";
		}
		catch(Exception e)
		{
			session.setAttribute("message", new Message("Enter Registered Email Only", "danger"));
			model.addAttribute("title", "Forgot Password - Contact Manager");
			return "forgot-password-form";
		}
	}
	
	@PostMapping("/verifyOTP")
	public String verifyOtp(@RequestParam("otp")Integer otp,HttpSession session,Model model)
	{
		
		Object sessionOtp = session.getAttribute("OTP");
		if(sessionOtp.equals(otp))
		{
		
			model.addAttribute("message","Change Password - Contact Manager");
			return "changePassword";
		}
		else {
			session.setAttribute("message", new Message("OTP Miss-Natched..!!", "danger"));
			model.addAttribute("title", "Verify OTP - Contact Manager");
			return "verify-otp";
		}
		
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("newPassword1")String newPassword1,
									@RequestParam("newPassword2")String newPassword2,
									Model model,HttpSession session)
	{
		Object email = session.getAttribute("email");
		User user = userRepo.findByUserName((String) email);
		if(newPassword1.equals(newPassword2))
		{
			user.setPassword(passwordEncoder.encode(newPassword1));
			userRepo.save(user);
			
			session.setAttribute("message", new Message("Password Changed Successfully", "success"));
			model.addAttribute("message","Change Password - Contact Manager");
			return "signin";
		}
		else
		{
			session.setAttribute("message", new Message("Password Miss-Matched..!!", "danger"));
			model.addAttribute("message","Change Password - Contact Manager");
			return "changePassword";
		}
		
		
	}
	
	
}
