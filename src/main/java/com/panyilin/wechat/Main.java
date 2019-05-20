package com.panyilin.wechat;


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
  		scheduler.config(SendMsgJob.class, "0 0 0/1 * * ?");
  		scheduler.start();
// 		String groupId = Test.getGroupIdByNickName("���Ѽ�");
//		WechatTools.sendMsgByUserName("��Һð�", groupId);
//		MessageTools.sendPicMsgByUserId(groupId, "D:/QR.jpg");
	}

	private static class MyHandler extends MsgHandlerAdapter{
		
		Core core = Core.getInstance();
		
		private UserRobotMap map = new UserRobotMap();
		
		private Logger logger = LoggerFactory.getLogger(DemoHandler.class);
		
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
		    String message = msg.getText();
		    if(!isGroupMsg && msg.getToUserName().equals(core.getUserName())) {
		    	if(message.equals("kq")) {
		    		map.enableRobot(from);
		    		talk = "���˲��ڣ� ����С�ֻ����ˡ� ��Ҫ�ر������룺 gb ��";
		    	}else if(message.equals("gb")) {
		    		map.disableRobot(from);
		    		talk = "С�ֻ����˺���˵�ټ��� ��Ҫ���������룺 kq ��";
		    	}else if(map.isRobotEnable(from)) {
		    		talk = message;
		    	} 
		    	map.count(from);
		    }
			return talk;
		}
		
		private String rollbackMsgHandler(BaseMsg msg) {
			//1.��������Ϣ����һ������ <msgid, message>�У� ���������������ֹ����� ��ʱ����
			//2.�жϴ���Ϣ�Ƿ���callback����
			//3.���ǣ��ó�����Ϣ��oldMessage ��xmlת�����
			//4.���������ң� �����鵽����Ϣ����֮�ҵ��ļ������С�
			MessageContainer<BaseMsg> messages = MessageContainer.getInstance();
			String displayName = MyUtils.getDisplayName(msg);
			if(msg.getType().equals(MsgTypeEnum.CALLBACK.getType())) {
				String id = MyUtils.getOldMsgIdBy(msg.getContent()); //
				BaseMsg base = messages.get(id);
				if(base == null) return null;
				displayName = MyUtils.getDisplayName(base);
				String text = base.getText();
				MessageTools.sendMsgById(String.format("%s��������Ϣ��%s",displayName, text), "filehelper");
			}else {
				messages.put(msg.getMsgId(), msg);
			}
			return null;
		}
	}

}
