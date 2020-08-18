package com.example.demo.job;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.demo.vo.Pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcPagingItemReaderJobConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource;
	
	private static final int chunkSize = 10;
	
	@Bean
	public Job jdbcPagingItemReaderJob() throws Exception {
		return jobBuilderFactory.get("jdbcPagingItemReaderJob")
				.start(jdbcPagingItemReaderStep())
				.build();
	}
	
	@Bean
	public Step jdbcPagingItemReaderStep() throws Exception {
		return stepBuilderFactory.get("jdbcPagingItemReaderStep")
				.<Pay, Pay>chunk(chunkSize)
				.reader(jdbcPagingItemReader())
				.writer(jdbcPagingItemWriter())
				.build();
	}
	
	@Bean
	public JdbcPagingItemReader<Pay> jdbcPagingItemReader() throws Exception {
		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("amount", 2000);
		
		return new JdbcPagingItemReaderBuilder<Pay>()
				.pageSize(chunkSize) //.pageSize(1000)
				//.fetchSize(chunkSize) //PagingItemReader에서는 pageSize 사용
				.dataSource(dataSource)
				.rowMapper(new BeanPropertyRowMapper<>(Pay.class))
				.queryProvider(createQueryProvider())
				.parameterValues(parameterValues) //쿼리에 대한 매개변수 값의 Map을 지정함
				.name("jdbcPagingItemReader")
				.build();
	}
	
	private ItemWriter<Pay> jdbcPagingItemWriter() {
		return list -> {
			for(Pay pay : list) {
				log.info("Current Pay={}", pay);
			}
		};
	}
	
	@Bean
	public PagingQueryProvider createQueryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean(); //SqlPagingQueryProviderFactoryBean을 통해 Datasource 설정값을 보고 Provider중 하나를 자동으로 선택
		queryProvider.setDataSource(dataSource); //Database에 맞는 PagingQueryProvider를 선택하기
		queryProvider.setSelectClause("id, amount, tx_name, tx_date_time");
		queryProvider.setFromClause("from pay");
		queryProvider.setWhereClause("where amount >= :amount"); //where에서 선언된 파라미터 변수명과 parameterValues에서 선언된 파라미터 변수명이 일치해야만 함
		
		Map<String, Order> sortKeys = new HashMap<>(1);
	    sortKeys.put("id", Order.ASCENDING);
		
		queryProvider.setSortKeys(sortKeys);
		
		return queryProvider.getObject();
		
	}
}

//JdbcCursorItemReader는 String타입으로 쿼리 생성, PagingItemReader는 PagingQueryProvider를 통해 쿼리 생성
//Database에는 Paging을 지원하는 자체적인 적략이 있음, 그래서 PagingQueryProvider 이걸 사용

