package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.vo.UserInfoVO;


public interface UserService extends UserDetailsService {

	Long save(UserInfoVO userInfoVO);
}
