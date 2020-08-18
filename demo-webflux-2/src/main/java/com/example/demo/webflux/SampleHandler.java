package com.example.demo.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.example.demo.repository.SampleRepository;
import com.example.demo.vo.Sample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SampleHandler {

	private final SampleRepository sampleRepository;

	@Autowired
	public SampleHandler(SampleRepository sampleRepository) {
		this.sampleRepository = sampleRepository;
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {

		Flux<Sample> sample = sampleRepository.findAll();
		
		return ServerResponse.ok()
				.contentType(APPLICATION_JSON)
				.body(sample, Sample.class);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {

		String id = request.pathVariable("id"); //이거 path()라고만 하면 안됨....
		
		return ServerResponse.ok()
				.contentType(APPLICATION_JSON)
				.body(sampleRepository.findById(String.valueOf(id)), Sample.class);

	}
	
//	public Mono<ServerResponse> findById(ServerRequest request) {
//		String id = String.valueOf(request.pathVariable("id"));
//		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//		Mono<Sample> sampleMono = sampleRepository.findById(id);
//		return sampleMono.flatMap(sample -> ServerResponse.ok()
//				.contentType(APPLICATION_JSON)
//				.body(BodyInserters.fromObject(sample)))
//				.switchIfEmpty(notFound);
//	}

}
