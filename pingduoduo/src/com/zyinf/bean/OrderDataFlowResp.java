package com.zyinf.bean;

public class OrderDataFlowResp {
	private String Code;
	private String Message;
	private String TaskID;
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getTaskID() {
		return TaskID;
	}
	public void setTaskID(String taskID) {
		TaskID = taskID;
	}
	
	
	@Override
	public String toString() {
		return "OrderResp [Code=" + Code + ", Message=" + Message + "]";
	}
}
