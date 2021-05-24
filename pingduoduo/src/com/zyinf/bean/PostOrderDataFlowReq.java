package com.zyinf.bean;

public class PostOrderDataFlowReq extends OrderDataFlowResp{

	private String status;
	private String outOrderNo;//拼多多订单编号
	private String orderNo;
	private String totalFee;
	private String mobile;
	private String prodNo;
	private String createTime;
	
	
	public String getStatus() {
//		订购状态: ACCEPT 受理成功
//		PROCESS 处理中
		return "ACCEPT";
	}
	public void setStatus(String status) {
		this.status = status;
	}
	//拼多多订单编号 外部订单编号 需要设置
	public String getOutOrderNo() {
		return outOrderNo;
	}
	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	
	// 商家订单编号
	public String getOrderNo() {
		return getTaskID();
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	//订单总金额 从package 里面计算
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	// 从请求出设置
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	//商家内部定单流量包编号  从package中获取
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	
	//自己计算
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public void setMoreData(String outOrderNo,String totalFee,String mobile ,String prodNo, String createTime){
		this.outOrderNo = outOrderNo;
		this.totalFee = totalFee;
		this.mobile = mobile;
		this.prodNo = prodNo;
		this.createTime = createTime;
	}
	
	
	@Override
	public String toString() {
		return "OrderResp [createTime=" + createTime + ", prodNo=" + prodNo + "]";
	}
}
