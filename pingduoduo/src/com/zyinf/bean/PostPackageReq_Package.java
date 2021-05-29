package com.zyinf.bean;

public class PostPackageReq_Package extends GetPackageResp_Package {
	
	//wzl  返回给拼多多的字符串
	private String PROD_NO;
	private String PROD_NAME;
	private String PROVINCE_NO;
	private String RES_TYPE = "1";
	private String DATA_FLOAT;
	private String PAR_PRICE;
	

	public String getPROD_NO() {
		return PROD_NO;
	}
	public void setPROD_NO(String pROD_NO) {
		PROD_NO = pROD_NO;
	}
	public String getPROD_NAME() {
		return PROD_NAME;
	}
	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}
	public String getPROVINCE_NO() {
		return PROVINCE_NO;
	}
	public void setPROVINCE_NO(String pROVINCE_NO) {
		PROVINCE_NO = pROVINCE_NO;
	}
	public String getRES_TYPE() {
		return RES_TYPE;
	}
	public void setRES_TYPE(String rES_TYPE) {
		RES_TYPE = rES_TYPE;
	}
	public String getDATA_FLOAT() {
		return DATA_FLOAT;
	}
	public void setDATA_FLOAT(String dATA_FLOAT) {
		DATA_FLOAT = dATA_FLOAT;
	}
	public String getPAR_PRICE() {
		return PAR_PRICE;
	}
	public void setPAR_PRICE(String pAR_PRICE) {
		PAR_PRICE = pAR_PRICE;
	}
	@Override
	public String toString() {
		return "GetPackageResp_Package [Name=" + PAR_PRICE + ", Package=" + PROD_NO
				+ ", PROD_NAME=" + RES_TYPE + ", DATA_FLOAT=" + DATA_FLOAT+ "]";
	}

}
