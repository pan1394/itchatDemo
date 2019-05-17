package com.panyilin.robot.job;

public class BizException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 14562L;
	
	private String errMsg;
  
	public BizException(String errMsg) {
		super(errMsg);
	}
	 
}

 
