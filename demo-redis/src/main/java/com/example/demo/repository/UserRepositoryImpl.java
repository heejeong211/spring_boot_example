package com.example.demo.repository;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.UserVO;

@Repository
public class UserRepositoryImpl implements UserRepository{

	private RedisTemplate<String, UserVO> redisTemplate;
	
	private HashOperations hashOperations;
	
	public UserRepositoryImpl(RedisTemplate<String, UserVO> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}
	
	@Override
	public void save(UserVO userVO) {
		
		hashOperations.put("User", userVO.getId(), userVO);
	}

	@Override
	public Map<String, UserVO> findAll() {
		
		return hashOperations.entries("User");
	}

	@Override
	public UserVO findById(String id) {
		
		return (UserVO) hashOperations.get("User", id);
	}

	@Override
	public void update(String id, UserVO userVO) {
		
		findById(id);
		save(userVO);
	}

	@Override
	public void delete(String id) {
		
		hashOperations.delete("User", id);
	}

}
