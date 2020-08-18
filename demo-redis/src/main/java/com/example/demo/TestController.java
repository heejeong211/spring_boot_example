package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

	/*@Autowired
    RedisTemplate<String, Object> redisTemplate;
	
	
	@RequestMapping(value = "/redis", method = RequestMethod.GET)
	public String list() {
		
		System.out.println("redis TestController!!");*/
//		
//		RedisClient redisClient = RedisClient
//				  .create("redis://mypass@localhost:6379/");
//				StatefulRedisConnection<String, String> connection = redisClient.connect();
//		
//		System.out.println("redis RedisClient!!");		
//		
//		RedisCommands<String, String> syncCommands = connection.sync();
//		syncCommands.set("key", "Hello, Redis!");
//		
//		System.out.println("redis syncCommands set!!");		
				
		
	/*
	 * ValueOperations<String, Object> vop = redisTemplate.opsForValue();
	 * vop.set("key5", "goooood");
	 * 
	 * System.out.println("redis redisTemplate set!!");
	 * 
	 * return "redis test"; }
	 */
	
}
