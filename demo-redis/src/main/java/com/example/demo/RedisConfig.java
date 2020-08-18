package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.demo.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

	/*
	 * @Bean public RedisConnectionFactory redisConnectionFactory() {
	 * LettuceConnectionFactory lettuceConnectionFactory = new
	 * LettuceConnectionFactory(); lettuceConnectionFactory.setPassword("mypass");
	 * return lettuceConnectionFactory; }
	 * 
	 * @Bean public RedisTemplate<String, Object> redisTemplate() {
	 * RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	 * redisTemplate.setConnectionFactory(redisConnectionFactory());
	 * redisTemplate.setKeySerializer(new StringRedisSerializer()); // *
	 * redisTemplate.setValueSerializer(new StringRedisSerializer().UTF_8); return
	 * redisTemplate; }
	 */

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

//	@Value("${spring.redis.password}")
//	private String redisPwd;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//		redisStandaloneConfiguration.setPassword(redisPwd); // redis에 비밀번호가 설정 되어 있는 경우 설정해주면 됩니다.
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, UserVO> redisTemplate() {
		RedisTemplate<String, UserVO> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer().UTF_8);
		return redisTemplate;
	}
	
	// 나중에 이거 한 번 시도해보자...
//	@Bean
//	public RedisTemplate<String, UserVO> redisTemplate() {
//		RedisTemplate<String, UserVO> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
//		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
//		redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
//		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
//		return redisTemplate;
//	}
}
