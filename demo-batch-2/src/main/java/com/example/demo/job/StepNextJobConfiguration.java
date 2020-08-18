package com.example.demo.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextJobConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job stepNextJob() {
		return jobBuilderFactory.get("stepNextJob")
				.start(step1())
				.next(step2())
				.next(step3())
				.build();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is Step1");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is Step2");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is Step3");
					return RepeatStatus.FINISHED;
				})
				.build();
	}	

}

//next()
//next()는 순차적으로 Step들을 연결시킬 때 사용 (step1->step2->step3)
//앞에 step에서 오류가 난다면 나머지 뒤에 있는 step들은 실행되지 못함
//하지만 상황에 따라 정상일 때는 Step B로 오류가 났을 때는 Step C로 수행해야 할 때가 있음
//이럴 경우를 대비해 Spring Batch Job에서는 조건별로 Step을 사용할 수 있음
