package com.fintech.tech_clone.quazrt.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

public class schedulerConfig {
    private final JobDetail jobDetail;

    private  final Trigger trigger;

    public schedulerConfig(@Qualifier("jobDetail") JobDetail jobDetail,@Qualifier("jobTrigger") Trigger trigger) {
        this.jobDetail = jobDetail;
        this.trigger = trigger;
    }
    @DependsOn({"jobDetail","jobTrigger"})
    @Bean
    public void  initialJob(){
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try{
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

        }catch (SchedulerException ex){
            ex.printStackTrace();
        }
    }
}
