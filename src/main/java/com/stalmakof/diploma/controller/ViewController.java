package com.stalmakof.diploma.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.stalmakof.diploma.common.LoginSession;
import com.stalmakof.diploma.common.Utility;
import com.stalmakof.diploma.models.User;
import com.stalmakof.diploma.repository.UserRepository;

@Controller
public class ViewController {

	@Autowired
	private UserRepository userRepository;

	static final Logger LOGGER = LogManager.getLogger(ViewController.class.getName());

	@RequestMapping("/")
	public String welcome() {
		return "welcome";
	}

	@RequestMapping("/login")
	public String login(){
		LoginSession.logout();
		return "login";
	}

	@RequestMapping("/tours")
	public String showTours(){
		return "index";
	}

	@RequestMapping(value = "/authlogin", method = RequestMethod.POST)
	public ModelAndView authLogin(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		LOGGER.info("Authenticate User = " + username);
		User user = this.getUser(username, password);
		if (user != null) {
			LoginSession.createSession(user);
			return new ModelAndView("redirect:/home");
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@RequestMapping("/home")
	public String home() {
		if (LoginSession.checkSession()) {
			return "home";
		} else {
			return "login";
		}
	}

	@RequestMapping("/bookings")
	public String bookings() {
		if (LoginSession.checkSession()) {
			return "bookings";
		} else {
			return "login";
		}
	}

	@RequestMapping("/users")
	public String users() {
		if (LoginSession.checkSession()) {
			return "users";
		} else {
			return "login";
		}
	}

	public User getUser(String username, String password) {
		Iterable<User> users = userRepository.findAll();
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(Utility.md5(password))) {
				return user;
			}
		}
		return null;
	}

	@RequestMapping("/createUser")
	public ModelAndView createUser(){
		User user = new User();
		user.setName("Danylo");
		user.setType("admin");
		user.setEmail("admin@email.com");
		user.setUsername("admin");
		user.setPassword(Utility.md5("admin123"));
		userRepository.save(user);
		return new ModelAndView("redirect:/login");
	}
}
