package com.example.demo.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@Configuration
public class SampleRouter {
	
	@Bean 
	public RouterFunction<ServerResponse> reoutBySample(SampleHandler handler) {
		return nest(path("/router"),
				route(GET("/").and(accept(APPLICATION_JSON)), handler::findAll)
				.andRoute(GET("/{id}").and(accept(APPLICATION_JSON)), handler::findById));
	}
	
//	@Bean
//	public RouterFunction<ServerResponse> routers(SampleHandler handler) {
//		return RouterFunctions
//				.route(GET("/webflux").and(accept(APPLICATION_JSON)), handler::findAll)
//				.andRoute(GET("/webflux/{id}").and(accept(APPLICATION_JSON)), handler::findById);
//				
//	}
	
}

//spring boot 내장 서버 기본 contextpath '/', port '8080'
//Application.java를 springboot로  실행해도 http://localhost:8080/router를 호출해도 404 NotFound 이다.
//spring-boot-starter-webflux는 RouterFunction beans에서 자동으로 디렉팅을 해준다고 한다.
//class sample위에 @configuration과, router() 메소드 위의 @Bean을 붙여준 후 다시 http://localhost:8080/router를 실행한다면 잘 돌아갈 것이다.
//... 하지만 난 안됨... 왜지???
//application.properties에 spring.main.web-application-type=reactive 이 부분 추가하기!!!!!!!