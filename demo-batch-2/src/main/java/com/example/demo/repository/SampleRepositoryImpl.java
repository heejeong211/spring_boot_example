package com.example.demo.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.Sample;

@Repository
public class SampleRepositoryImpl implements SampleRepository{
	
private RedisTemplate<String, Sample> redisTemplate;
	
	private HashOperations hashOperations;
	
	public SampleRepositoryImpl(RedisTemplate<String, Sample> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(Sample sample) {
		
		hashOperations.put("Sample", sample.getId(), sample);
		
	}

}
