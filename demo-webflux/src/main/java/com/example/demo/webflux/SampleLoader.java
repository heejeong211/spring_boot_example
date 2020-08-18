package com.example.demo.webflux;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class SampleLoader {
	
	private final ReactiveRedisConnectionFactory factory;
	private final ReactiveRedisOperations<String, Sample> sampleOps;
	
	public SampleLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Sample> sampleOps) {
		this.factory = factory;
		this.sampleOps = sampleOps;
	}
	
	@PostConstruct
	public void loadData() {
		factory.getReactiveConnection().serverCommands().flushAll().thenMany(
			Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
				.map(name -> new Sample(UUID.randomUUID().toString(), name))
				.flatMap(sample -> sampleOps.opsForValue().set(sample.getId(), sample)))
			.thenMany(sampleOps.keys("*")
					.flatMap(sampleOps.opsForValue()::get))
			.subscribe(System.out::println);
		
	}

}
//애플리케이션을 여러 번(다시) 시작할 수 있으므로, 이전 실행에서 여전히 존재할 수 있는 데이터를 먼저 제거해야 함. -> 진짜로 redis에 있던 sample 찾아보니까 없어져 있었음...
//이 작업은 flushAll() (Redis) 서버 명령으로 수행한다.
//기존 데이터를 플러시하면 작은 Flux 샘플을 생성하고 각 생플 이름을 Sample 객체에 매핑한 후 반응성 Redis 리포지토리에 저장한다.
//그런 다음 리포지토리에 모든 값을 쿼리하여 표시한다.