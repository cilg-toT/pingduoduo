package com.zyinf.bean;

public class PingDuoDuoResult<T> {
	private String resultCode;
	private String resultMsg;
	
	private T resultData;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}
	
	
}
