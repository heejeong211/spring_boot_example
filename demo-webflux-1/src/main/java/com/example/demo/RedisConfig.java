package com.example.demo;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.example.demo.vo.Sample;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

//	@Value("${spring.redis.password}")
//	private String redisPwd;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofSeconds(2)).shutdownTimeout(Duration.ZERO).build();
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setPassword(redisPwd);
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort), clientConfig);
	}

	@Bean
	public ReactiveRedisTemplate<String, Sample> sampleRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {

		 Jackson2JsonRedisSerializer<Sample> serializer = new Jackson2JsonRedisSerializer<>(Sample.class);
	     RedisSerializationContextBuilder<String, Sample> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
	     RedisSerializationContext<String, Sample> serializationContext = builder
	    		 .key(new StringRedisSerializer())
	    		 .value(serializer)
	    		 .hashKey(new StringRedisSerializer())
	    		 .hashValue(serializer)
	    		 .build();

		return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
	}

}
