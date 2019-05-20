package com.panyilin.robot.counter;

public class Counter {
	
	private boolean enable = false;
	
	private int i = 0;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

    public int count() {
    	return i++;
    }

    public void reset() {
    	i = 0;
    }

}
