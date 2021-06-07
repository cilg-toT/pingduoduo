package com.zyinf.bean.telFee;

import com.zyinf.bean.GetPackageResp;

public class MYOrderTelFeeByPhoneReq  {

	private String action;
	private String v;
	private String outTradeNo;
	private String account;
	private String mobile;
	private String Package;
	private String sign;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPackage() {
		return Package;
	}
	public void setPackage(String package1) {
		Package = package1;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	
	

	
}
