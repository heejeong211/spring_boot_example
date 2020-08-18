package com.example.demo.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.vo.Teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProcessorNullJobConfiguration {

	public static final String JOB_NAME = "processorNullBatch";
	public static final String BEAN_PREFIX = JOB_NAME + "_";
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory emf;
	
	@Value("${chunkSize:1000}")
	private int chunkSize;
	
	@Bean(JOB_NAME)
	public Job job() {
		return jobBuilderFactory.get(JOB_NAME)
				.preventRestart()
				.start(step())
				.build();
	}
	
	@Bean(BEAN_PREFIX + "step")
	@JobScope
	public Step step() {
		return stepBuilderFactory.get(BEAN_PREFIX + "step")
				.<Teacher, Teacher>chunk(chunkSize)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	@Bean
	public JpaPagingItemReader<Teacher> reader() {
		return new JpaPagingItemReaderBuilder<Teacher>()
				.name(BEAN_PREFIX+"reader")
				.entityManagerFactory(emf)
				.pageSize(chunkSize)
				.queryString("SELECT t FROM Teacher t")
				.build();
	}
	
	@Bean
	public ItemProcessor<Teacher, Teacher> processor() {
		return teacher -> {
			boolean isIgnoreTarget = teacher.getId() % 2 == 0L;
			if(isIgnoreTarget) {
				log.info(">>>>> Teacher name={}, isIgnoreTarget={}", teacher.getTName(), isIgnoreTarget);
				return null;
			}
			
			return teacher;
		};
	}
	
	private ItemWriter<Teacher> writer() {
		return items -> {
			for(Teacher item : items) {
				log.info("Teacher Name={}", item.getTName());
			}
		};
	}
	
}

//필터
//Writer에 값을 넘길지 말지를 Processor에서 판단하는 것
//위 코드는 Teacher의 id가 짝수일 경우 필터링하는 예제임 => 결과적으로 홀수만 출력