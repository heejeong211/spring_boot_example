package com.example.demo.webflux;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rest/*")
public class SampleRestController {
	
	private final ReactiveRedisOperations<String, Sample> sampleOps;

	public SampleRestController(ReactiveRedisOperations<String, Sample> sampleOps) {
		this.sampleOps = sampleOps;
	}
	
	@GetMapping("/samples")
	public Flux<Sample> all() {
		return sampleOps.keys("*")
				.flatMap(sampleOps.opsForValue()::get);
	}
}

//Spring Data Redis 및 Project Reactor 를 사용하여 Redis 데이터 저장소와 반응적으로 상호작용하여
//Sample을 차단하지 않고 객체를 저장하고 검색하는 Spring 애플리케이션을 빌드한다.
//이 응용 프로그램은 Publisher Reactive Streams 사양에 따라 Reactor의 구현을 사용한다.
//Mono(publisher가 0 또는 1의 값을 반환하는 경우)
//Flux(publisher가 0-n 값을 봔한하는 경우)