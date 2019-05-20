package com.panyilin.robot.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.core.Core;

public class SendMsgJob implements Job{

	Logger logger = LoggerFactory.getLogger(SendMsgJob.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Core core = Core.getInstance();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String msg = String.format("ÐÄÌø¼ì²â£º %s", sdf.format(new Date()));
    	MessageTools.sendMsgById(msg, core.getUserName());
    	logger.info(msg);
    }

}
