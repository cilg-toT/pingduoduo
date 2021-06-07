package com.zyinf.service;

import java.util.Map;

import com.zyinf.bean.GetPackageResp_Package;
import com.zyinf.bean.PostPackageReq_Package;
public interface MaiYuanService {
	
	public String getAccount();
	public String getKey();
	public String getLiuLiangUrl();
	
	public String getTelFeeAccount();
	public String getTelFeeKey();
	public String getTelFeeUrl();
	
	public String getMYSign(Map<String ,String>param );
	
	public String getMYTelFeeSign(Map<String ,String>param);
	
	//根据 流量包 获取流量类型 0 全国漫游流量 1 全国非漫游流量 2省内漫游流量 3省内非漫游流量 不带参数时默认为0
	public PostPackageReq_Package getPackageRespon(String prodNo);
	
	public PostPackageReq_Package getPackageRespon(String resType,String dataFloat,String expireDay );

	
	public String MD5(String s);
	
	public String PDD_Md5(Map<String, String> params, String lastKey);
	
	public String getTimeStamp();
	
}
