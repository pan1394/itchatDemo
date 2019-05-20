package com.panyilin.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panyilin.robot.utils.MyUtils;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;

public class DemoHandler implements IMsgHandlerFace {

	private Logger logger = LoggerFactory.getLogger(DemoHandler.class);
	
	@Override
	public String textMsgHandle(BaseMsg msg) { 
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
			logger.info("{}:{}", displayName, msg.getText()); 
		}
		
		return null;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
