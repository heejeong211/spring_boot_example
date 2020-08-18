package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.UserService;
import com.example.demo.vo.UserInfoVO;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String homeView() {
		return "pages/home";
	}
	
	@GetMapping("/login")
	public String loginView() {
		return "pages/login";
	}
	
	@GetMapping("/signup")
	public String signupView() {
		return "pages/signup";
	}
	
	@PostMapping("/signup")
	public String signup(UserInfoVO userInfoVO) {
		userService.save(userInfoVO);
		return "redirect:/login";
	}
	
	@GetMapping("/member/info")
	public String userInfoView() {
		return "pages/user_info";
	}
	
	@GetMapping("/admin")
	public String adminView() {
		return "pages/admin";
	}
	
	@GetMapping("/denied")
	public String deniedView() {
		return "pages/denied";
	}
	
}
