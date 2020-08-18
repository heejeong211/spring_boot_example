package com.example.demo.test;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repository.SampleRepository;
import com.example.demo.vo.Sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestJobConfiguration {
	
	@Autowired
	private SampleRepository sampleRepository;

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory; //JPA를 사용하기 때문에 영속성을 위해 EntityManager를 이용해야 함
	
	private static final int chunkSize = 10;
	
	@Bean
	public Job jpaItemWriterJob() {
		return jobBuilderFactory.get("testJob")
				.start(jpaItemWriterStep())
				.build();
	}
	
	@Bean
	public Step jpaItemWriterStep() {
		return stepBuilderFactory.get("jpaItemWriterStep")
				.<Sample, Sample>chunk(chunkSize)
				.reader(jpaItemWriterReader())
				.processor(jpaItemProcessor())
				.writer(jpaItemWriter())
				.build();
	}
	
	@Bean
	public JpaPagingItemReader<Sample> jpaItemWriterReader() {
		return new JpaPagingItemReaderBuilder<Sample>()
				.name("jpaItemWriterReader")
				.entityManagerFactory(entityManagerFactory)
				.pageSize(chunkSize)
				.queryString("SELECT s FROM Sample s")
				.build();
	}
	
	@Bean
	public ItemProcessor<Sample, Sample> jpaItemProcessor() {
		
		return new itemSample();
	}
	
	@Bean
	public ItemWriter<Sample> jpaItemWriter() {
		
		return new ItemWriter<Sample>() {

			@Override
			public void write(List<? extends Sample> items) throws Exception {
					
				for (Sample item : items) {
		    		Sample test = (Sample) item;
		    		sampleRepository.save(test); //시리얼라이징 필요(그래서 redisConfig에서 해줌)
		    		
		    		
		    	}
			}
			
		};
	}
}

//JpaItemWriter는 JdbcBatchItemWriter와 달리 넘어온 Entity를 데이터베이스에 반영함 => 그래서 processor가 필요
//즉, JpaItemWriter는 Entity 클래스를 제네릭 타입으로 받아야만 함
//JdbcBatchItemWriter의 경우 VO(DTO) 클래스를 받더라도 sql로 지정된 쿼리가 실행되니 문제가 없겠지만, JpaItemWriter는 넘어온 Item을 그대로 entityManager.merge()로 테이블에 반영하기 때문


//--job.name=testJob version=30 31부터 하면 됨