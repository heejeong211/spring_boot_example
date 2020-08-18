package com.example.demo.repository;

import java.util.Map;

import com.example.demo.vo.UserVO;

public interface UserRepository {

	void save(UserVO userVO); //입력
	Map<String, UserVO> findAll(); //조회
	UserVO findById(String id); //하나만 조회
	void update(String id, UserVO userVO); //수정
	void delete(String id); //삭제
	
}
