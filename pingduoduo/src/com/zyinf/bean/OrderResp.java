package com.zyinf.bean;

public class OrderResp {
	private String Code;
	private String Message;
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
	@Override
	public String toString() {
		return "OrderResp [Code=" + Code + ", Message=" + Message + "]";
	}

}
