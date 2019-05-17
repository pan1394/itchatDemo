package com.panyilin.wechat;


import org.quartz.SchedulerException;

import com.panyilin.robot.job.ClearJob;
import com.panyilin.robot.scheduler.QuartzSchedulerUtils;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;

public class Main {

	public static void main(String[] args) throws InterruptedException, SchedulerException {
		IMsgHandlerFace handler = new DemoHandler();
		Wechat demo = new Wechat(handler, "D://");
		demo.start();
		
 		Thread.sleep(5000);
		
  		QuartzSchedulerUtils.execute(ClearJob.class, "0 5 * * * ?");
// 		String groupId = Test.getGroupIdByNickName("老友记"); 
//		WechatTools.sendMsgByUserName("大家好啊", groupId);
//		MessageTools.sendPicMsgByUserId(groupId, "D:/QR.jpg");
		
		
		
	}
	
	
}
