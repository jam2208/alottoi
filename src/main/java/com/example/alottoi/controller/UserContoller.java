package com.example.alottoi.controller;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.alottoi.model.User;
import com.example.alottoi.repository.UserRepository;

@Controller
public class UserContoller {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	HttpSession session;

 	@GetMapping("/email-check")
  	@ResponseBody
  	public String emailCheck(@ModelAttribute User user) {
    	
		String email = user.getEmail();
    	Optional<User> dbUser = userRepository.findByEmail(email);

		if(dbUser.isPresent()) {
      		return "unavailable";
    	} else {
      		return "available";
    	}
	}

	@GetMapping("/checkinfo")
	public String checkinfo() {
		return "checkinfo";
	}

	@PostMapping("/checkinfo")
	public String checkinfoPost(@ModelAttribute User user) {
		User dbUser = (User) session.getAttribute("user_info");

		if(dbUser != null) {

			String dbemail = dbUser.getEmail();
			String userEmail = user.getEmail();
			boolean isMatchEmail = dbemail.equals(userEmail);

			String encodedPwd = dbUser.getPwd();
			String userPwd = user.getPwd();
			boolean isMatchPwd = passwordEncoder.matches(userPwd, encodedPwd);

			if (isMatchPwd && isMatchEmail) {
				return "redirect:/modifyinfo";
			} else {
				return "alert1";
			}
		} else {
			return "guide2";
		}
	}

	@GetMapping("/modifyinfo")
	public String modifyinfo() {
		return "modifyinfo";
	}

	@PostMapping("/modifyinfo")
	public String modifyinfoPost(@ModelAttribute User user) {
		User dbUser = (User) session.getAttribute("user_info");

		if (dbUser != null) {

			dbUser.setName(user.getName());

			String userPwd = user.getPwd();
			String encodedPwd = passwordEncoder.encode(userPwd);
			dbUser.setPwd(encodedPwd);

			dbUser.setEmail(user.getEmail());
			dbUser.setPhnNum(user.getPhnNum());

			Date currentDateTime = new Date();
			dbUser.setDtModify(currentDateTime);

			userRepository.save(dbUser);

			return "/guide1";
		} else {
			return "/guide2";
		}
	}

	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

	@PostMapping("/signin")
	public String signinPost(@ModelAttribute User user) {
		Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

		if (dbUser.isPresent()) {

		String encodedPwd = dbUser.get().getPwd();
		String userPwd = user.getPwd();
		boolean isMatch = passwordEncoder.matches(userPwd, encodedPwd);

		if (isMatch) {
			session.setAttribute("user_info", dbUser.get());
			return "redirect:/";
		} else {
			return "/alert1";
		}
	} else {
		return "/alert2";
	}
		
	}

	@GetMapping("/signout")
	public String signout() {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute User user) {
		Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

			String userPwd = user.getPwd();
			String encodedPwd = passwordEncoder.encode(userPwd);
			user.setPwd(encodedPwd);

			Date currentDateTime = new Date();
			user.setDtSignup(currentDateTime);

			userRepository.save(user);
		
		return "redirect:/";

	}
}
