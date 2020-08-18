package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoWebflux1Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebflux1Application.class, args);
		
		//스프링 프레임워크 버전 체크
		String springVersion = org.springframework.core.SpringVersion.getVersion();
		System.out.println("스프링 프레임워크 버전 : " + springVersion);
	}

}
