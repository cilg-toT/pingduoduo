package com.zyinf.bean;

public class GetPackageResp_Package {
	private String Package;
	private String Name;
	private String Price;
	private String Type;
	
	
	public String getPackage() {
		return Package;
	}
	public void setPackage(String package1) {
		Package = package1;
		PROD_NO = Package;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
		PROD_NAME = name;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
		PAR_PRICE = price;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
	
	//wzl  返回给拼多多的字符串
	private String PROD_NO;
	private String PROD_NAME;
	private String PROVINCE_NO;
	private String RES_TYPE;
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
		return "GetPackageResp_Package [Name=" + Name + ", Package=" + Package
				+ ", Price=" + Price + ", Type=" + Type + "]";
	}

}
