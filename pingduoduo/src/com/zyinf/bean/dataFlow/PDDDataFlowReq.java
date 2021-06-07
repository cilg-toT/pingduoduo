package com.zyinf.bean.dataFlow;


public class PDDDataFlowReq  {

	private String outOrderNo;
	private String prodNo;
	private String amount;
	
	private String mobile;
	private String notifyUrl;
	
	private String resType;
	private String dataFloat;
	private String expireDay;
	public String getOutOrderNo() {
		return outOrderNo;
	}
	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
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
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getDataFloat() {
		return dataFloat;
	}
	public void setDataFloat(String dataFloat) {
		this.dataFloat = dataFloat;
	}
	public String getExpireDay() {
		return expireDay;
	}
	public void setExpireDay(String expireDay) {
		this.expireDay = expireDay;
	}
	
}
