package com.zyinf.bean.telFee;

import com.zyinf.bean.GetPackageResp;

public class PDDOrderTelFeeByPhoneReq  {

	
	private String outOrderNo;
	//话费面额
	private String parPrice;
	//数量
	private String amount;
	private String mobile;
	private String notifyUrl;
	
	
	public String getOutOrderNo() {
		return outOrderNo;
	}
	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	public String getParPrice() {
		return parPrice;
	}
	public void setParPrice(String parPrice) {
		this.parPrice = parPrice;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	

	
}
