package com.fintech.tech_clone.quazrt.detail;

import com.fintech.tech_clone.quazrt.job.BootJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobDetailConfig {
    @Bean(name = "jobDetail")
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(BootJob.class)
                .withIdentity("Run Quartz","Job_Group")
                .withDescription("")
                .storeDurably(true)
                .build();

    }
}
