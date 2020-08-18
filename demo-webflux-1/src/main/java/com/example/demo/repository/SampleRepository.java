package com.example.demo.repository;

import com.example.demo.vo.Sample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SampleRepository {
	
	Flux<Sample> findAll();
	Mono<Sample> findById(String id);

}
