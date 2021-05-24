package com.zyinf.service;

import com.zyinf.bean.GetPackageResp;
import com.zyinf.bean.OrderDataFlowResp;
import com.zyinf.bean.PostOrderDataFlowReq;
public interface MyService {
	public GetPackageResp getPackages(String type);
	
//	根据proNo 定流量包
	public PostOrderDataFlowReq getOrderDataFlowResp(String mobile,String notifyUrl,String outOrderNo,String proNo);
//	根据 dataFloat resType 和expireDay 定流量包
	public PostOrderDataFlowReq getOrderDataFlowResp(String mobile,String notifyUrl,String outOrderNo,String resType,String dataFloat,String expireDay); 
}
