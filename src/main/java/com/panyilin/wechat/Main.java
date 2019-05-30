package com.panyilin.wechat;


import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panyilin.robot.counter.UserRobotMap;
import com.panyilin.robot.job.ClearJob;
import com.panyilin.robot.job.SendMsgJob;
import com.panyilin.robot.scheduler.QuartzScheduler;
import com.panyilin.robot.utils.MyUtils;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;

public class Main {
	 
	public static void main(String[] args) throws InterruptedException, SchedulerException {
		Wechat wechat = new Wechat(new MyHandler(), "D://");
		wechat.start();

 		Thread.sleep(5000);

  		QuartzScheduler scheduler = QuartzScheduler.getInstance();
  		scheduler.config(ClearJob.class,   "0 0/10 * * * ?");
  		scheduler.config(SendMsgJob.class, "0 0 0/2 * * ?");
  		scheduler.start();
// 		String groupId = Test.getGroupIdByNickName("老友记");
//		WechatTools.sendMsgByUserName("大家好啊", groupId);
//		MessageTools.sendPicMsgByUserId(groupId, "D:/QR.jpg");
	}

	private static class MyHandler extends MsgHandlerAdapter{
		
		Core core = Core.getInstance();
		
		private UserRobotMap map = new UserRobotMap();
		
		private Logger logger = LoggerFactory.getLogger(MyHandler.class);
		
		@Override
		public String textMsgHandle(BaseMsg msg) {
			logger.info("{}:{}", MyUtils.getDisplayName(msg), msg.getText()); 
			rollbackMsgHandler(msg);
			return robotHandler(msg);
		}
		
		private String robotHandler(BaseMsg msg) {
			String talk = null;
		    boolean isGroupMsg = msg.isGroupMsg();
		    String from = msg.getFromUserName();
		    String message = StringUtils.trimToEmpty(msg.getText());
		    if(!isGroupMsg && core.getUserName().equals(msg.getToUserName())) {
		    	if(message.equals("kq")) {
		    		map.enableRobot(from);
		    		talk = "主人不在， 我是小林机器人。 需要关闭请输入： gb 。";
		    	}else if(message.equals("gb")) {
		    		map.disableRobot(from);
		    		talk = "小林机器人和你说再见。 需要开启请输入： kq 。";
		    	}else if(map.isRobotEnable(from)) {
		    		talk = message;
		    	} 
		    	map.count(from);
		    }
			return talk;
		}
		
		private String rollbackMsgHandler(BaseMsg msg) {
			//1.将所有消息放在一个容器 <msgid, message>中， ？如何清理容器防止溢出， 定时清理。
			//2.判断此消息是否是callback类型
			//3.如是，拿出次消息的oldMessage ？xml转义解析
			//4.从容器查找， 并将查到的消息发送之我的文件助手中。
			MessageContainer<BaseMsg> messages = MessageContainer.getInstance();
			String displayName = MyUtils.getDisplayName(msg);
			if(msg.getType().equals(MsgTypeEnum.CALLBACK.getType())) {
				String id = MyUtils.getOldMsgIdBy(msg.getContent()); //
				BaseMsg base = messages.get(id);
				if(base == null) return null;
				displayName = MyUtils.getDisplayName(base);
				String text = base.getText();
				MessageTools.sendMsgById(String.format("%s撤回了消息：%s",displayName, text), "filehelper");
			}else {
				messages.put(msg.getMsgId(), msg);
			}
			return null;
		}
	}

}
