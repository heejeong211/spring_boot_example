package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
public class HelloBatchJob {
	
	private final String BATCH_NAME = "HelloBatchJob_";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	//HelloBatchJob_ 이라는 Job 만듦
	@Bean
	public Job job() {
		return jobBuilderFactory.get(BATCH_NAME).start(step1()).next(step2()).build();
	}

	//HelloBatchJob_ 이라는 Job의 Step1 실행
	//tasklet = itemReder + itemProcessor + itemWriter
	@Bean
	public Step step1() {
		return stepBuilderFactory.get(BATCH_NAME + "Step1").tasklet((stepContribution, chunkContext) -> {
			System.out.println(BATCH_NAME + "Step1 Started");
			return RepeatStatus.FINISHED;
		}).build();
	}

	//HelloBatchJob_ 이라는 Job의 Step2 실행
	@Bean
	public Step step2() {
		return stepBuilderFactory.get(BATCH_NAME + "Step2").tasklet((stepContribution, chunkContext) -> {
			System.out.println(BATCH_NAME + "Step2 Started");
			return RepeatStatus.FINISHED;
		}).build();
	}

}
