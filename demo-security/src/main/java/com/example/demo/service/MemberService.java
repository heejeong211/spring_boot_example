package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.vo.MemberVO;

public interface MemberService extends UserDetailsService{
	Integer save(MemberVO memberVO);
}
