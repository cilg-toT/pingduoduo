package com.zyinf.bean;

import java.util.Arrays;

public class GetPackageResp extends OrderResp {
	public GetPackageResp_Package[] Packages;

	public GetPackageResp_Package[] getPackages() {
		return Packages;
	}
	public void setPackages(GetPackageResp_Package[] packages) {
		Packages = packages;
	}
	@Override
	public String toString() {
		return "GetPackageResp [Packages=" + Arrays.toString(Packages)
				+ ", getCode()=" + getCode() + ", getMessage()=" + getMessage()
				+ "]";
	}

	
}
