package com.beyond.basic.board.post.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PostBatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job job;
    public PostBatchScheduler(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void batchScheduler(){//job을 실행
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);//Job을 실행하는 인스턴스 job이 같은Job이면 안되므로 비교 시간으로 분류
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
