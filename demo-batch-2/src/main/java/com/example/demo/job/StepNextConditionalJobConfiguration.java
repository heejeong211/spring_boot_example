package com.example.demo.job;

import org.springframework.batch.core.ExitStatus;
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
public class StepNextConditionalJobConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job stepNextConditionalJob() {
		return jobBuilderFactory.get("stepNextConditionalJob")
				.start(conditionalJobStep1())
					.on("FAILED") //ExitStatus가 FAILED 일 경우
					.to(conditionalJobStep3()) //step3으로 이동(다음으로 이동할 Step 지정)
					.on("*") //step3의 결과와 관계없이
					.end() //step3으로 이동하면 Flow가 종료됨
				.from(conditionalJobStep1()) //step1로 부터 (일종의 리스너 역할, 상태값을 보고 일치하는 상태라면 to()에 포함된 step 호출, step1의 이벤트 캐치가 FAILED로 되있는 상태에서 추가로 이벤트 캐치 하려면 from을 써야만 함)
					.on("*") //FAILED포함 모든 경우(모든 ExitStatus)
					.to(conditionalJobStep2()) //step2로 이동
					.next(conditionalJobStep3()) //step2가 정상 종료되면 step3으로 이동
					.on("*") //step3의 결과와 관계없이
					.end() //step3으로 이동하면 Flow가 종료됨 , on("*") 뒤에 있는 end는  FlowBuilder를 반환하는 end, FlowBuilder를 반환하는 end 사용시 계속해서 from으로 이어나갈 수 있음
				.end() //Job도 종료, build() 앞에 있는 end는 FlowBuilder를 종료하는 end
				.build();
	}
	
	@Bean
	public Step conditionalJobStep1() {
		return stepBuilderFactory.get("conditionalJobStep1")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step1");
					/**
					 	ExitStatus를 FAILED로 지정함
					 	해당 status를 보고 flow가 진행됨
					 **/
					contribution.setExitStatus(ExitStatus.FAILED); //이 부분 주석처리 하면 step1, step2, step3 순차적으로 실행함(조건별 흐름제어)
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step conditionalJobStep2() {
		return stepBuilderFactory.get("conditionalJobStep2")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step2");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step conditionalJobStep3() {
		return stepBuilderFactory.get("conditionalJobStep3")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is stepNextConditionalJob Step3");
					return RepeatStatus.FINISHED;
				})
				.build();
	}

}

//이 방법에는 문제가 있음
//Step이 담당하는 역할이 2개 이상이 됨
//실제 Step이 처리해야할 로직 외에도 분기처리를 시키기 위해 ExitStatus 조작이 필요함

//다양한 분기 로직 처리의 어려움
//ExitStatus를 커스텀하게 고치기 위해선 Listener를 생성하고 Job Flow에 등학하는 등 번거로움이 존재

//=>Spring Batch에서는 Step들의 Flow속에서 분기만 담당하는 타입이 있음(JobExecutionDecider)
