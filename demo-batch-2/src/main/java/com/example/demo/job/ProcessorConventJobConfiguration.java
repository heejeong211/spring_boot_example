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
public class ProcessorConventJobConfiguration {

	public static final String JOB_NAME = "ProcessorConvertBatch";
	public static final String BEAN_PREFIX = JOB_NAME + "_";
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory emf;
	
	@Value("${chunkSize:1000}")
	private int chunkSize;
	
	@Bean
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
				.<Teacher, String>chunk(chunkSize)
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
	public ItemProcessor<Teacher, String> processor() {
		return teacher -> {
			return teacher.getTName();
		};
	}
	
	private ItemWriter<String> writer() {
		return items -> {
			for (String item : items) {
				log.info("Teacher Name={}", item);
			}
		};
	}
	
}

//변환
//Reader에서 읽은 타입을 변환하여 Writer에 전달해주는 것을 말함
//Teacher라는 VO 클래스를 읽어와 Name 컬럼 (String 타입)을 Writer에 넘겨주도록 구성한 코드
