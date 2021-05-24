package com.zyinf.service;

import com.zyinf.bean.GetPackageResp_Package;
public interface MaiYuanService {
	
	public String getAccount();
	public String getKey();
	
	public String getSign(String mobile ,String packageStr);
	
	//根据 流量包 获取流量类型 0 全国漫游流量 1 全国非漫游流量 2省内漫游流量 3省内非漫游流量 不带参数时默认为0
	public GetPackageResp_Package getPackageRespon(String prodNo);
	
	public GetPackageResp_Package getPackageRespon(String resType,String dataFloat,String expireDay );
	
	
	
	
}
