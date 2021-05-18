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
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	@Override
	public String toString() {
		return "GetPackageResp_Package [Name=" + Name + ", Package=" + Package
				+ ", Price=" + Price + ", Type=" + Type + "]";
	}

}
