package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import com.example.demo.vo.UserInfo;
import com.example.demo.vo.UserInfoVO;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	//Spring Security 필수 메소드 구현
	//@param account 
	//@return UserDetails
	//@throws UsernameNotFoundException 유저가 없을 때 예외 발생
	
	//Security에서 지정한 Service이기 때문에 이 메소드를 필수로 구현
	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException { 
		
		return userRepository.findByAccount(account)
				.orElseThrow(()->new UsernameNotFoundException((account)));
	}
	
	//회원정보 저장
	//@param userInfoVO 회원정보가 들어있는 VO
	//@return 저장되는 회원의 PK

	@Override
	public Long save(UserInfoVO userInfoVO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfoVO.setPassword(encoder.encode(userInfoVO.getPassword()));
		
		return userRepository.save(UserInfo.builder()
				.name(userInfoVO.getName())
				.account(userInfoVO.getAccount())
				.auth(userInfoVO.getAuth())
				.password(userInfoVO.getPassword()).build()).getId(); //괄호조심..
	}
	
	

}
