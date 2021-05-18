package com.zyinf.service;

import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zyinf.bean.FlowPara;
import com.zyinf.bean.GetPackageResp;

public class MyServiceImpl implements MyService {
	static Logger log = Logger.getLogger(MyServiceImpl.class.getName());
	
	public void init() throws Exception {
	}
	
	public void destroy() throws Exception {
	}

	// 演示如何发出http请求，并将其json内容转为为对象
	private FlowPara getCmpayPtfs(String phone) throws Exception {
		String url = "https://www.cmpay.com/pes/flow_para.xhtml?viewCode=json&PTF_USR_ID=" + phone + 
							"&PTF_DIS_TYP=0";
		log.debug(url);
		String resp = Request.Get(url).
					execute().returnContent().asString();		
		return new Gson().fromJson(resp, FlowPara.class);		
	}

	@Override
	public GetPackageResp getPackages(String type) {
		return null;
	}

}
