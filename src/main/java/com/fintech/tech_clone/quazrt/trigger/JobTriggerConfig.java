package com.fintech.tech_clone.quazrt.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobTriggerConfig {
    @Bean(name = "jobTrigger")
    public Trigger jobTriggerBoot(@Qualifier("jobDetail") JobDetail jobDetail){
        System.out.println("----------------Run---------------");
        try {
            String time = "0/10 * * * * ?";
            return TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity("Run Quartz","Job_Group")
                    .startNow().withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
        }catch (Exception e){
            System.out.println("--------Error Trigger-------- "+ e.getMessage());
            return null;
        }
    }
}
