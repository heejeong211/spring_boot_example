package com.example.demo.repository;

import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.Sample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class SampleRepositoryImpl implements SampleRepository{

	private ReactiveRedisTemplate<String, Sample> sampleRedisTemplate;
	
	private ReactiveHashOperations hashOperations;
	
	public SampleRepositoryImpl(ReactiveRedisTemplate<String, Sample> sampleRedisTemplate) {
		this.sampleRedisTemplate = sampleRedisTemplate;
		hashOperations = sampleRedisTemplate.opsForHash();
	}


	@Override
	public Flux<Sample> findAll() {
		
		return hashOperations.entries("Sample");
	}

	@Override
	public Mono<Sample> findById(String id) {
		
		return (Mono<Sample>) hashOperations.get("Sample", id);
	}

}


//한동안 계속 null이 나왔다.. 그 이유는 SampleRepositoryImpl에 기본 생성자가 있었기 때문이다....(도대체 누가 넣으라고 했어!!!!!)