package com.fintech.tech_clone.quazrt.job;

import com.fintech.tech_clone.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class BootJob implements Job {
    @Autowired
    private UserService userService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("----------------Run Quartz Job ----------------");
        userService.deleteUserIfExpried();
    }
}
