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

import com.example.demo.vo.Pay;
import com.example.demo.vo.Pay2;
import com.example.demo.vo.Sample;

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

//	@Bean
//	public RedisTemplate<String, Sample> redisTemplate() {
//		RedisTemplate<String, Sample> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer()); //setHashValue어쩌구에  new Srtring어쩌구 였는데, 이렇게 하니까 Sample을 String으로 캐스트 못한다고 뜸.. 그래서 이렇게 바꿔주니까 됨...
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new StringRedisSerializer().UTF_8);
//		return redisTemplate;
//	}
	
	@Bean
	public RedisTemplate<String, Sample> redisTemplate() {
		RedisTemplate<String, Sample> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Sample.class));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Sample.class));
		return redisTemplate;
	}
	
	
	
	
}
