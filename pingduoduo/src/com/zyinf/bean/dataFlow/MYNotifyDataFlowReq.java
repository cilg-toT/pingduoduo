package com.zyinf.bean.dataFlow;

import com.zyinf.bean.GetPackageResp;

public class MYNotifyDataFlowReq  {

	private String TaskID;

	private String Mobile;

	private String Status;

	private String ReportTime;

	private String ReportCode;

	private String OutTradeNo;

	private String Sign;

	public String getTaskID() {
		return TaskID;
	}

	public void setTaskID(String taskID) {
		TaskID = taskID;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getReportTime() {
		return ReportTime;
	}

	public void setReportTime(String reportTime) {
		ReportTime = reportTime;
	}

	public String getReportCode() {
		return ReportCode;
	}

	public void setReportCode(String reportCode) {
		ReportCode = reportCode;
	}

	public String getOutTradeNo() {
		return OutTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		OutTradeNo = outTradeNo;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}
	
}
