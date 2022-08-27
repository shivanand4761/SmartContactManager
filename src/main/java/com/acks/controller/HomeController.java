package com.acks.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.acks.dao.UserRepository;
import com.acks.helper.Message;
import com.acks.model.Users;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test")
	//@ResponseBody
	public String test()
	{
		Users users = new Users();
		users.setName("Akash Shendge");
		users.setEmail("akashshendge@gmail.com");
		
		userRepository.save(users);
		
		return "working";
	}
	
	@RequestMapping("/home")
	public String homeHandler(Model model)
	{
		model.addAttribute("title", "Home-Smarter Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String aboutHandler(Model model)
	{
		model.addAttribute("title", "About-Smarter Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signupHandler(Model model)
	{
		model.addAttribute("title", "SignUp-Smarter Contact Manager");
		model.addAttribute("users", new Users());
		
		return "signup";
	}
	
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String testHandler(@Valid @ModelAttribute("users") Users users, BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session)
	{
		try {
			if(!agreement)
			{
				System.out.println("you have not agreed the term and conditions");
				throw new Exception("you have not agreed the term and conditions");
			}
			
			if (result.hasErrors()) {
				System.out.println(result);
				model.addAttribute("users", users);
				return "signup";
			}
			
			System.out.println("agreement : "+agreement);
			System.out.println("User : "+users);
			
			users.setRole("ROLE_USER");
			users.setEnabled(true);
			users.setImageUrl("default.png");
			users.setPassword(passwordEncoder.encode(users.getPassword()));
			
			Users save = this.userRepository.save(users);
			
			model.addAttribute("users", new Users());
			session.setAttribute("message", new Message("Registered Successfully!!", "alert-success"));
			
			return "signup";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("users", users);
			session.setAttribute("message", new Message("Something Went Wrong!!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
		
		//return "signup";
	}
	
	//Login Handler
	@GetMapping("/signin")
	public String loginHandler(Model model)
	{
		model.addAttribute("title", "login");
		return "login";
	}

}
