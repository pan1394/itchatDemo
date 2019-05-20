package com.panyilin.robot.counter;

import java.util.HashMap;

/**
 * 
 * @author panyl
 *
 */
public class UserRobotMap extends HashMap<String, Counter> {

	public void count(String key) {
		Counter counter = this.get(key);
		if(counter != null && counter.isEnable()) {
			counter.count();
		}
	}
	
	public void enableRobot(String key) {
		Counter c = new Counter();
		c.setEnable(true);
		this.count(key);
		this.put(key, c);
	}
	
	public void disableRobot(String key) {
		Counter c = new Counter();
		this.put(key, c);
	}
 
	public boolean isRobotEnable(String key) {
		Counter c = get(key);
		boolean isEnable = false;
		if(c != null)
			isEnable = c.isEnable();
		return isEnable;
	}
	
	public boolean isFirst(String key) {
		Counter c = get(key);
		boolean isFirst = false;
		if(c != null && c.isEnable())
			isFirst = c.count() == 1 ? true : false;
		return isFirst;
	}
	
	@Override
	public Counter get(Object key) {
		return super.get(key);
	}

	@Override
	public Counter put(String key, Counter value) {
		return super.put(key, value);
	}

	
	
}
