package com.example.demo.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.vo.Pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPagingItemReaderJobConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory; //dataSource 말고 entityManagerFactory사용
	
	private int chunkSize = 10;
	
	@Bean
	public Job jpaPagingItemReaderJob() {
		return jobBuilderFactory.get("jpaPagingItemReaderJob")
				.start(jpaPagingItemReaderStep())
				.build();
	}
	
	@Bean
	public Step jpaPagingItemReaderStep() {
		return stepBuilderFactory.get("jpaPagingItemReaderStep")
				.<Pay, Pay>chunk(chunkSize)
				.reader(jpaPagingItemReader())
				.writer(jpaPagingItemWriter())
				.build();
	}
	
	@Bean
	public JpaPagingItemReader<Pay> jpaPagingItemReader() {
		return new JpaPagingItemReaderBuilder<Pay>()
				.name("jpaPagingItemReader")
				.entityManagerFactory(entityManagerFactory)
				.queryString("SELECT p FROM Pay p WHERE amount >= 2000")
				.build();
	}
	
	private ItemWriter<Pay> jpaPagingItemWriter() {
		return list -> {
			for(Pay pay : list) {
				log.info("Current Pay={}", pay);
			}
		
		};
	}

}

//PagingItemReader 주의사항 - 정렬(Order)가 무조건 포함되어 있어야 함

//JpaRepository를 ListItemReader, QueueItemReader에 사용하면 안됨
//간혹 JPA의 조희 쿼리를 쉽게 구현하기 위해 JpaRepository를 이용해서 new ListItemReader<>(jpaRepository.findByAge(age))로 Reader를 구현하는데,
//이렇게 할 경우 Spring Batch의 장점인 paging & Cursor 구현이 없어 대규모 데이터 처리가 불가능함
//만약 정말 JpaRepository를 써야 한다면 RepositoryItemReader를 사용하자
//Hibernate, JPA 등 영속성 컨텍스트가 필요한 Reader 사용시 fetchSize와 ChunkSize는 같은 값을 유지해야 함