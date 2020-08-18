package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.SampleRepository;
import com.example.demo.vo.Sample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/redis/*")
public class SampleRestController {

	@Autowired
	private SampleRepository sampleRepository;
	
	@GetMapping("/sample")
	public Flux<Sample> findAll() {
		
		return sampleRepository.findAll();
	}
	
	@GetMapping("/sample/{id}")
	public Mono<Sample> findById(@PathVariable String id) {
		
		return sampleRepository.findById(id);
	}
}

//Flux는 Map과 비슷함.. Map을 사용하고 싶어서 Flux<Map<String, Sample>> 이런식으로 써서 오류났었음...