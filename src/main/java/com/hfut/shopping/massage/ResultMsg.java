package com.hfut.shopping.massage;

public enum  ResultMsg {

	successMsg,errorMsg;
	
	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public ResultMsg setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	
}
