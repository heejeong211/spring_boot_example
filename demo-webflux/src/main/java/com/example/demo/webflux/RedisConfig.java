package com.example.demo.webflux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Value("${spring.redis.password}")
	private String redisPwd;

//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//		redisStandaloneConfiguration.setPassword(redisPwd); // redis에 비밀번호가 설정 되어 있는 경우 설정해주면 됩니다.
//		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
//		return lettuceConnectionFactory;
//	}
	
	//반응성 Redis 작업을 지원하는 Spring Beans
	 @Bean
	  ReactiveRedisOperations<String, Sample> redisOperations(ReactiveRedisConnectionFactory factory) {
	    Jackson2JsonRedisSerializer<Sample> serializer = new Jackson2JsonRedisSerializer<>(Sample.class);

	    RedisSerializationContext.RedisSerializationContextBuilder<String, Sample> builder =
	        RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

	    RedisSerializationContext<String, Sample> context = builder.value(serializer).build();

	    return new ReactiveRedisTemplate<>(factory, context);
	  }
	
}
