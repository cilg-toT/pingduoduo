package com.zyinf.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zyinf.bean.FlowPara;
import com.zyinf.bean.GetPackageResp;
import com.zyinf.bean.GetPackageResp_Package;
import com.zyinf.bean.OrderDataFlowResp;
import com.zyinf.bean.PostOrderDataFlowReq;
import com.zyinf.service.MaiYuanService;
import com.zyinf.service.MaiYuanServiceImpl;


public class MyServiceImpl implements MyService {
	static Logger log = Logger.getLogger(MyServiceImpl.class.getName());
	
	static MaiYuanService maiYuanService = new MaiYuanServiceImpl();
	static String orderDataFlowUrl = "http://host.com/api.aspx";
	
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

	@Override
	public PostOrderDataFlowReq getOrderDataFlowResp(String mobile,
			String notifyUrl,String outOrderNo, String proNo) {
		// TODO Auto-generated method stub
		GetPackageResp_Package packageRespon = maiYuanService.getPackageRespon(proNo);
		return this.getOrderDataFlowResp(mobile, notifyUrl, outOrderNo, packageRespon);
	}

	@Override
	public PostOrderDataFlowReq getOrderDataFlowResp(String mobile,
			String notifyUrl,String outOrderNo, String resType, String dataFloat, String expireDay) {
		
		GetPackageResp_Package packageRespon = maiYuanService.getPackageRespon(resType, dataFloat, expireDay);
		return this.getOrderDataFlowResp(mobile, notifyUrl, outOrderNo, packageRespon);
	}
	
	private PostOrderDataFlowReq getOrderDataFlowResp(String mobile,String notifyUrl,String outOrderNo,GetPackageResp_Package packageRespon){
		Form form = Form.form().add("Action", "charge").add("v", "1.1");
		form.add("range", packageRespon.getType());
		form.add("OutTradeNo", outOrderNo);
		//默认是1
		form.add("PackageType", "1");
		 
		form.add("account", maiYuanService.getAccount()).add("mobile", mobile).add("package", packageRespon.getPackage());
		form.add("sign", maiYuanService.getSign(mobile, packageRespon.getPackage()));
		
		try {
			String resp = Request.Post(orderDataFlowUrl).bodyForm(form.build()).execute().returnContent().asString();
			PostOrderDataFlowReq data =  new Gson().fromJson(resp, PostOrderDataFlowReq.class);
			
			Date date = new Date();
			data.setMoreData(outOrderNo, packageRespon.getPrice(), mobile, packageRespon.getPackage(), date.toLocaleString());
			return data ;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	

	
	
	




}
