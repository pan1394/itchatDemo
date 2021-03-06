package com.panyilin.robot.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panyilin.wechat.MessageContainer;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;

public class ClearJob implements Job{
	
	Logger logger = LoggerFactory.getLogger(ClearJob.class);
	
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException { 
    	MessageContainer<BaseMsg> messages = MessageContainer.getInstance();
    	messages.clear();
    	logger.info("������Ϣ���档");
    }
    
}
