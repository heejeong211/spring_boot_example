package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.MemberService;
import com.example.demo.vo.MemberVO;

@Controller
@RequestMapping("/")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
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
	public String signup(MemberVO memberVO) {
		memberService.save(memberVO);
		return "redirect:/login";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER, ROLE_ADMIN')")
	@GetMapping("/member/info")
	public String userInfoView() {
		return "pages/user_info";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	public String adminView() {
		return "pages/admin";
	}
	
	@GetMapping("/denied")
	public String deniedView() {
		return "pages/denied";
	}
	
}
