package com.panyilin.robot.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzSchedulerUtils {

	public static void execute(Class<? extends Job> clazz, String cronExpression) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity("cronTriggerDetail", "cronTriggerDetailGrounp").build();

        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("name", "cronTriggerMap");
        jobDataMap.put("group", "cronTriggerGrounp");
        
        CronTrigger cronTrigger = TriggerBuilder.  
                                    newTrigger().
                                    withIdentity("cronTrigger", "cronTrigger").
                                    withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)). // 使用任务调度器的 CronScheduleBuilder 来生成一个具体的 CronTrigger 对象
                                    build();
        
        scheduler.scheduleJob(jobDetail, cronTrigger);
        scheduler.start();
	}
}
