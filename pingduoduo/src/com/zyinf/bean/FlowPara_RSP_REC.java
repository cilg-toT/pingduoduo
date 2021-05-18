package com.zyinf.bean;

import java.util.Date;

public class FlowPara_RSP_REC {
	public static String CmPay = "cmpay.com";
	public static String CmccShop = "shop.10086";
	
	private String PTF_AMT;
	private String DAT_FLOW;
	private String PTF_NO;
	private String DISCOUNT_AMT;
	private String PROV_CD;
	private String PTF_TYP;
	private String source;
	private String phone;
	private Date crtTime;
	public static String getCmPay() {
		return CmPay;
	}
	public static void setCmPay(String cmPay) {
		CmPay = cmPay;
	}
	public static String getCmccShop() {
		return CmccShop;
	}
	public static void setCmccShop(String cmccShop) {
		CmccShop = cmccShop;
	}
	public String getPTF_AMT() {
		return PTF_AMT;
	}
	public void setPTF_AMT(String pTFAMT) {
		PTF_AMT = pTFAMT;
	}
	public String getDAT_FLOW() {
		return DAT_FLOW;
	}
	public void setDAT_FLOW(String dATFLOW) {
		DAT_FLOW = dATFLOW;
	}
	public String getPTF_NO() {
		return PTF_NO;
	}
	public void setPTF_NO(String pTFNO) {
		PTF_NO = pTFNO;
	}
	public String getDISCOUNT_AMT() {
		return DISCOUNT_AMT;
	}
	public void setDISCOUNT_AMT(String dISCOUNTAMT) {
		DISCOUNT_AMT = dISCOUNTAMT;
	}
	public String getPROV_CD() {
		return PROV_CD;
	}
	public void setPROV_CD(String pROVCD) {
		PROV_CD = pROVCD;
	}
	public String getPTF_TYP() {
		return PTF_TYP;
	}
	public void setPTF_TYP(String pTFTYP) {
		PTF_TYP = pTFTYP;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	@Override
	public String toString() {
		return "FlowPara_RSP_REC [DAT_FLOW=" + DAT_FLOW + ", DISCOUNT_AMT="
				+ DISCOUNT_AMT + ", PROV_CD=" + PROV_CD + ", PTF_AMT="
				+ PTF_AMT + ", PTF_NO=" + PTF_NO + ", PTF_TYP=" + PTF_TYP
				+ ", crtTime=" + crtTime + ", phone=" + phone + ", source="
				+ source + "]";
	}

}
