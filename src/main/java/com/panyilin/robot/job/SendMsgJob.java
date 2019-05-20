package com.panyilin.robot.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhouyafeng.itchat4j.api.MessageTools;

public class SendMsgJob implements Job{

	Logger logger = LoggerFactory.getLogger(SendMsgJob.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String msg = String.format("心跳检测： %s", sdf.format(new Date()));
    	MessageTools.sendMsgById(msg, "filehelper");
    	logger.info("清理消息缓存。");
    }

}
