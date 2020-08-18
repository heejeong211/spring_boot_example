package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.UserRepository;
import com.example.demo.vo.UserVO;

@RestController
@RequestMapping("/rest/*")
public class UserRestController {

	@Autowired
	private UserRepository userRepository;

	//입력
	@PostMapping("/add")
	public void add(UserVO userVO) {
		
		userRepository.save(userVO);
	}

	//수정
	@PutMapping("/{id}")
	public UserVO update(@PathVariable("id") final String id, UserVO userVO) {
		userRepository.update(id, userVO);

		return userRepository.findById(id);
	}

	//조회
	@GetMapping("/all")
	public Map<String, UserVO> all() {

		return userRepository.findAll();
	}
	
	//하나만 조회
	@GetMapping("/{id}")
	public UserVO findById(@PathVariable("id") final String id) {

		return userRepository.findById(id);
	}
	
	//삭제
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") final String id) {
		
		userRepository.delete(id);
	}
	
	
}
