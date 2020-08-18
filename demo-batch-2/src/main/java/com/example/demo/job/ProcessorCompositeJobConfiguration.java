package com.example.demo.job;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.vo.Teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProcessorCompositeJobConfiguration {

	public static final String JOB_NAME = "processorCompositeBatch";
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
				.<Teacher, String>chunk(chunkSize)
				.reader(reader())
				.processor(compositeProcessor())
				.writer(writer())
				.build();
	}
	
	@Bean
	public JpaPagingItemReader<Teacher> reader() {
		return new JpaPagingItemReaderBuilder<Teacher>()
				.name(BEAN_PREFIX + "reader")
				.entityManagerFactory(emf)
				.pageSize(chunkSize)
				.queryString("SELECT t FROM Teacher t")
				.build();
	}
	
	@Bean
	public CompositeItemProcessor compositeProcessor() {
		List<ItemProcessor> delegates = new ArrayList<>(2);
		delegates.add(processor1());
		delegates.add(processor2());
		
		CompositeItemProcessor processor = new CompositeItemProcessor<>();
		
		processor.setDelegates(delegates);
		
		return processor;
	}
	
	public ItemProcessor<Teacher, String> processor1() {
		return Teacher::getTName;
	}
	
	public ItemProcessor<String, String> processor2() {
		return t_name -> "안녕하세요. " + t_name + "입니다.";
	}
	
	private ItemWriter<String> writer() {
		return items -> {
			for(String item : items) {
				log.info("Teacher Name={}", item);
			}
		};
	}
	
}

//Spring Batch에서는 자주 사용하는 용도의 Processor를 미리 클래스로 만들어서 제공하고 있음
//ItemProcessorAdapter
//VaildatingItemProcessor
//CompositeItemProcessor
//하지만 최근에는 Processor를 직접 구현하는 일이 많아 잘 안씀, 그러나 CompositeItemProcessor는 간혹 필요할 때가 있음
//CompositeItemProcessor는 ItemProcessor간의 체이닝을 지원하는 Processor임
//Processor의 역할은 변환 혹은 필터인데, 변환이 2번 필요하다면 하나의 Processor의 역활이 너무 커지기 때문에 CompositeItemProcessor를 만들게 됨

//위 코드는 Teacher의 이름을 가져와(getTName()) 이름 앞/뒤에 문자 ("안녕하세요. " + t_name + "입니다.")를 붙여 Writer에 전달하는 코드임