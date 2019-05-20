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

public class QuartzScheduler {

	private Scheduler scheduler;

	private static QuartzScheduler instance = new QuartzScheduler();

	private QuartzScheduler() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public static QuartzScheduler getInstance() {
		return instance;
	}

	public void start() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void config(Class<? extends Job> clazz, String cronExpression) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(clazz).
        		//withIdentity("cronTriggerDetail", "cronTriggerDetailGrounp").
        		build();

        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("name", "cronTriggerMap");
        jobDataMap.put("group", "cronTriggerGrounp");

        CronTrigger cronTrigger = TriggerBuilder.
                                    newTrigger().
                                    //withIdentity("cronTrigger", "cronTrigger").
                                    withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

         scheduler.scheduleJob(jobDetail, cronTrigger);
	}
}
