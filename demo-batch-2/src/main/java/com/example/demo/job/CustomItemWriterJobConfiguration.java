package com.example.demo.job;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.vo.Pay;
import com.example.demo.vo.Pay2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CustomItemWriterJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory; //JPA를 사용하기 때문에 영속성을 위해 EntityManager를 이용해야 함
	
	private static final int chunkSize = 10;
	
	@Bean
	public Job customItemWriterJob() {
		return jobBuilderFactory.get("customItemWriterJob")
				.start(customItemWriterStep())
				.build();
	}
	
	@Bean
	public Step customItemWriterStep() {
		return stepBuilderFactory.get("customItemWriterStep")
				.<Pay, Pay2>chunk(chunkSize)
				.reader(customItemWriterReader())
				.processor(customItemProcessor())
				.writer(customItemWriter())
				.build();
	}
	
	@Bean
	public JpaPagingItemReader<Pay> customItemWriterReader() {
		return new JpaPagingItemReaderBuilder<Pay>()
				.name("customItemWriterReader")
				.entityManagerFactory(entityManagerFactory)
				.pageSize(chunkSize)
				.queryString("SELECT p FROM Pay p")
				.build();
	}
	
	@Bean
	public ItemProcessor<Pay, Pay2> customItemProcessor() {
		return pay -> new Pay2(pay.getAmount(), pay.getTxName(), pay.getTxDateTime());
	}
	
	@Bean
	public ItemWriter<Pay2> customItemWriter() {
		return new ItemWriter<Pay2>() {

			@Override
			public void write(List<? extends Pay2> items) throws Exception {
				for (Pay2 item : items) {
					System.out.println(item);
				}
				
			}
			
		};
	}
}

//Reader와 달리 Writer에 경우 Custom하게 구현해야 할 일이 많음
//예를 들면
//Reader에서 읽어온 데이터를 RestTemplate으로 외부 API로 전달해야 할 때
//임시저장을 하고 비교하기 위해 싱글톤 객체에 값을 넣어야할 때
//여러 Entity를 동시에 save 해야할 때
//이렇게 Spring Batch에서 공식적으로 지원하지 않는 Writer를 사용하고 싶을 때 ItemWriter인터페이스를 구현하면 됨

//ItemWriter를 사용할 때 Processor에서 Writer에 List를 전달하고 싶을 때, ItemWriter의 제네릭으로 List로 선언해서는 문제를 해결할 수 없음
//https://jojoldu.tistory.com/140 참고



//JpaItemWriter는 JdbcBatchItemWriter와 달리 넘어온 Entity를 데이터베이스에 반영함 => 그래서 processor가 필요
//즉, JpaItemWriter는 Entity 클래스를 제네릭 타입으로 받아야만 함
//JdbcBatchItemWriter의 경우 VO(DTO) 클래스를 받더라도 sql로 지정된 쿼리가 실행되니 문제가 없겠지만, JpaItemWriter는 넘어온 Item을 그대로 entityManager.merge()로 테이블에 반영하기 때문



//--job.name=processorCompositeBatch version=14