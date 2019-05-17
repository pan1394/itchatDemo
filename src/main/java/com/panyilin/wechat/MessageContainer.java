package com.panyilin.wechat;

import java.util.concurrent.ConcurrentHashMap;

public class MessageContainer<T> {

	private static MessageContainer instance = new MessageContainer<>();
	
	private MessageContainer() {
		messages = new ConcurrentHashMap<>();
	};
	
	public static MessageContainer getInstance() {
		return instance;
	}
	
	private ConcurrentHashMap<String, T> messages;
	
	public void put(String messageId, T msg) {
		messages.put(messageId, msg);
	}
	
	public T get(String messageId) {
		return messages.get(messageId);
	}
	
	public void clear() {
		messages.clear();
	}
	
}
