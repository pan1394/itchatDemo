package com.panyilin.robot.utils;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;

import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;

public class MyUtils {

	private static Core core = Core.getInstance();
	
	public static String getGroupIdByNickName(String name) {
		String groupId = "";
		if(StringUtils.isNotBlank(name)) {
			JSONObject obj = WechatTools.getGroupList().stream().filter( o-> name.equals(o.get("NickName"))).findAny().get();
			groupId = obj.getString("UserName");
		}
		return groupId;
	}
	
	public static String getDisplayedNameByUserName(String userName) {
		String name = "";
		if(StringUtils.isNotBlank(userName)) {
			// self
			if(core.getUserName().equals(userName)) return core.getUserSelf().getString("NickName");
			// friends
			name = core.getUserDisplayNameMap().get(userName);
			if(StringUtils.isNotBlank(name)) {
				return name;
			}
			else {
				name = userName;
			} 
		}
		return name;
	}
	
	public static String getDisplayedNameByUserName2(String userName) {
		String name = "";
		if(StringUtils.isNotBlank(userName)) {
			// self
			if(core.getUserName().equals(userName)) return core.getUserSelf().getString("NickName");
			// friends
			JSONObject obj = core.getUserInfoMap().get(userName);
			if(obj != null) {
				String rName = obj.getString("RemarkName");
				String nName = obj.getString("NickName");
				name = StringUtils.isNotBlank(rName) ? rName : nName;
			}
			
			// public users
			// special users
			else {
				name = userName;
			} 
		}
		return name;
	}
	
	public static String getName(JSONObject object) {
		Objects.requireNonNull(object);
		String remarkName = object.getString("RemarkName");
		String nickName = object.getString("NickName");
		return StringUtils.isNotBlank(remarkName) ? remarkName : nickName;
	}
	
	public static String getOldMsgIdBy(String xmlString) {
		if(StringUtils.isBlank(xmlString)) return "";
		String res = StringEscapeUtils.unescapeXml(xmlString);
		Document document ;
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(new InputSource(new StringReader(res)));
			NodeList nodes = document.getElementsByTagName("msgid");
			String txt = nodes.item(0).getTextContent();
			return txt;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
}
