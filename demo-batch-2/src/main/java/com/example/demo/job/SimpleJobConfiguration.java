package com.example.demo.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j //log 사용을 위한 lombok 어노테이션
@RequiredArgsConstructor //생성자 DI를 위한 lombok 어노테이션
@Configuration
public class SimpleJobConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	//simpleJob 이란 이름의 Batch Job을 생성함, job의 이름은 별도로 지정하지 않고 Builder를 통해 지정함
	@Bean
	public Job simpleJob() {
		return jobBuilderFactory.get("simpleJob")
				.start(simpleStep1(null))
				.next(simpleStep2(null))
				.build();
	}
	
	//simpleStep1 이란 이름의 Batch Step을 생성함, Builder를 통해 이름을 지정함
	//.tasklet((contribution, chunkContext) Step 안에서 수행될 기능을 명시, Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할 때 사용
	//여기서는 Batch가 수행되면 log.info(">>>>> This is Step1")가 출력되도록 함
	@Bean
	@JobScope
	public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
		return stepBuilderFactory.get("simpleStep1")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is Step1");
					log.info(">>>>> requestDate = {}", requestDate);
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	@JobScope
	public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
		return stepBuilderFactory.get("simpleStep2")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is Step2");
					log.info(">>>>> requestDate = {}", requestDate);
					return RepeatStatus.FINISHED;
				})
				.build();
	}
}
//-> 표시 (람다식)

//Job_Instance
//같은 Batch Job이라도 Job Parameter가 다르면 Batch_JOB_INSTANCE에 기록되며, Job Parameter가 같다면 기록되지 않음

//Job_Execution
//JOB_EXECUTION은 JOB_INSTACE가 성공/실패했던 모든 내역을 가지고 있음
//Job Parameter reqeustDate=20200729 로 생성된 Batch_JOB_INSTANCE(id=4)가 2번 실행되었고, 첫 번째 실패, 두 번째 성공했다는 것을 알 수 있음
//동일한 Job Parameter로 2번 실행했는데 같은 Parameter로 실행되었다는 에러가 발생하지 않음
//Spring Batch는 동일한 Job Parameter로 성공한 기록이 있을때만 재수행이 안된다는 것을 알 수 있음

//Job Parameter
//Job Parameter를 사용하기 위해서는 항상 Spring Batch 전용 Scope를 선언해야 함(@JobScope, @StepScope)
//@Value("#{jobParameters[파라미터명]}")
//@JobScope는 Step선언문에서
//@StepScope는 Tasklet이나 ItemReader, ItemProcessor, ItemWriter에서 사용 가능