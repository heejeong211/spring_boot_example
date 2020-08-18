package com.example.demo.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.demo.vo.Pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcCursorItemReaderJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource;
	
	private static final int chunkSize = 10;
	
	@Bean
	public Job jdbcCursorItemReaderJob() {
		return jobBuilderFactory.get("jdbcCursorItemReaderJob")
				.start(jdbcCursorItemReaderStep())
				.build();
	}
	
	@Bean
	public Step jdbcCursorItemReaderStep() {
		return stepBuilderFactory.get("jdbcCursorItemReaderStep")
				.<Pay, Pay>chunk(chunkSize) //첫번째 Pay는 Reader에서 반환할 타입이고, 두번째 Pay는 Writer에 파라미터로 넘어올 타입을 말함
				.reader(jdbcCursorItemReader()) //Database에서 한번에 가져올 데이터 양을 나타냄
				.writer(jdbcCursorItemWriter())
				.build();
	}
	
	@Bean
	public JdbcCursorItemReader<Pay> jdbcCursorItemReader() {
		return new JdbcCursorItemReaderBuilder<Pay>()
				.fetchSize(chunkSize)
				.dataSource(dataSource) //Database에 접근하기 위해 사용할 Datasource 객체를 할당함
				.rowMapper(new BeanPropertyRowMapper<>(Pay.class)) //뭐리 결과를 Java 인스턴스로 매핑하기 위한 Mapper임, 커스텀하게 생성해서 사용할 수 도 있지만 이렇게 될 경우 매번 Mapper 클래스를 생성해야 되서 보편적으로는 Spring에서 공식적으로 지원하는 BeanPropertyRowMapper.class를 많이 사용함
				.sql("SELECT id, amount, tx_name, tx_date_time FROM pay") //Reader로 사용할 쿼리문을 사용
				.name("jdbcCursorItemReader") //reader의 이름을 지정, Bean의 이름이 아니며 Spring Batch의 ExecutionContext에서 저장되어질 이름
				.build();
	}
	
	private ItemWriter<Pay> jdbcCursorItemWriter() {
		return list -> {
			for (Pay pay: list) {
				log.info("Current Pay={}", pay);
			}
		};
	}
}
//CursorItemReader의 주의사항
//CursorItemReader를 사용하실때는 Database와 SocketTimeout을 충분히 큰 값으로 설정해야 함
//Cursor는 하나의 Connection으로 Batch가 끝날때까지 사용되기 때문에 Batch가 끝나기 전에 Database와 어플리케이션의 Connection이 먼저 끊어질 수 있음
//따라서 Batch 수행시간이 오래 걸리는 경우에는 PagingItemReader를 사용하는게 나음
//Paging의 경우 한 페이지를 읽을때마다 Connection을 맺고 끊기 때문에 아무리 ㅁ낳은 데이터라도 타임아웃과 부하 없이 수행될 수 있음
