package com.zyinf.service.dataFlow;

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
import com.zyinf.bean.PingDuoDuoResult;
import com.zyinf.bean.PostOrderDataFlowReq;
import com.zyinf.bean.dataFlow.MYDataFlowReq;
import com.zyinf.bean.dataFlow.MYDataFlowRsp;
import com.zyinf.bean.dataFlow.MYNotifyDataFlowReq;
import com.zyinf.bean.dataFlow.PDDDataFlowReq;
import com.zyinf.bean.dataFlow.PDDDataFlowRsp;
import com.zyinf.bean.telFee.MYNotifyReq;
import com.zyinf.bean.telFee.MYOrderTelFeeByPhoneReq;
import com.zyinf.bean.telFee.MYOrderTelFeeByPhoneRsp;
import com.zyinf.bean.telFee.PDDOrderTelFeeByPhoneRsp;
import com.zyinf.service.MaiYuanService;
import com.zyinf.service.MaiYuanServiceImpl;


public class DataFlowServiceImpl implements DataFlowService {
	static Logger log = Logger.getLogger(DataFlowServiceImpl.class.getName());
	
	static MaiYuanService maiYuanService = new MaiYuanServiceImpl();
	static String PDD_SecretKey = "E47D6A7C2E8EDDE0F303919B694DEA67";
	
	
	public void init() throws Exception {
	}
	
	public void destroy() throws Exception {
	}
	
	@Override
	public PingDuoDuoResult<PDDDataFlowRsp> requstMYDataFlow(
			PDDDataFlowReq req1) {
		// TODO Auto-generated method stub
		MYDataFlowReq req = this.getMYDataFlowReq(req1);
		Form form = Form.form().add("Action", "charge").add("v", "1.1");
		form.add("range", req.getRange());
		form.add("OutTradeNo", req.getOutTradeNo());
		//默认是1
		form.add("PackageType",req.getPackageType());
		 
		form.add("account", req.getAccount()).add("mobile", req.getMobile()).add("package", req.getPackage());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("account", req.getAccount());
		map.put("mobile", req.getMobile());
		map.put("package",req.getPackage());
		form.add("sign", maiYuanService.getMYSign(map));
		
		try {
			String resp = Request.Post(maiYuanService.getLiuLiangUrl()).bodyForm(form.build()).execute().returnContent().asString();
			MYDataFlowRsp data =  new Gson().fromJson(resp, MYDataFlowRsp.class);
			PDDDataFlowRsp rsp = this.getPDDDataFlowRsp(req1, data);
			
			PingDuoDuoResult<PDDDataFlowRsp> result = new PingDuoDuoResult<PDDDataFlowRsp>();
			result.setResultCode(data.getCode());
			result.setResultMsg(data.getMessage());
			result.setResultData(rsp);
			return result;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	@Override
	public String requstPDDDataFlowNotify(MYNotifyDataFlowReq req) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String status = req.getStatus().equals("4")?"SUCCESS":"FAIL";
		
		Map<String,String> map = new HashMap<String,String>();
				
		map.put("order_sn", req.getOutTradeNo());
		map.put("outer_order_sn", req.getTaskID());
		map.put("status", status);
		map.put("mall_id", "637786");
		Date date = new Date();
		String timestamp = (date.getTime()/1000-60)+"";
		map.put("type", "pdd.virtual.mobile.charge.notify");
		map.put("data_type", "JSON");
		map.put("timestamp", timestamp);
				
		String sign = maiYuanService.PDD_Md5(map,PDD_SecretKey);
				
		String PDD_Url="http://open.yangkeduo.com/api/router?type=pdd.virtual.mobile.charge.notify&data_type=JSON&timestamp="+timestamp
						+"&order_sn="+req.getOutTradeNo()
						+"&outer_order_sn="+req.getTaskID()
						+"&status="+status
						+"&mall_id=637786&sign="+sign;
				
		try {
			System.out.println("拼多多请求："+PDD_Url);
			String resp = Request.Post(PDD_Url).execute().returnContent().asString();
			System.out.println("拼多多回调数据："+resp);
			return resp;
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAIL";
	}
	
	public PDDDataFlowRsp getPDDDataFlowRsp(PDDDataFlowReq req1,MYDataFlowRsp rsp1){
		PDDDataFlowRsp rsp = new PDDDataFlowRsp();
		
		rsp.setMobile(req1.getMobile());
		rsp.setOutOrderNo(req1.getOutOrderNo());
		rsp.setOrderNo(rsp1.getTaskID());
		rsp.setTotalFee(req1.getProdNo()==null?req1.getProdNo():req1.getDataFloat());
		rsp.setProdNo(req1.getProdNo()==null?req1.getProdNo():req1.getDataFloat());
		rsp.setCreateTime(new Date().getTime()+"");
		rsp.setStatus(rsp1.getCode().equals("0")?"PROCESS":"ACCEPT");
		
		return rsp;
	}
	public MYDataFlowReq getMYDataFlowReq(PDDDataFlowReq req1){
		
		MYDataFlowReq req = new MYDataFlowReq();
		
		req.setAction("charge");
		req.setV("1.1");
		
		req.setOutTradeNo(req1.getOutOrderNo());
		req.setPackage("1");
		req.setAccount(maiYuanService.getAccount());
		req.setMobile(req1.getMobile());
		
		if(req1.getProdNo() == null){
			req.setPackage(req1.getDataFloat());
			req.setRange("0");
		}else{
			req.setPackage(req1.getProdNo());
			if(req1.getResType() == "2"){
				req.setRange("1");
			}else{
				req.setRange("0");
			}
		}
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("account",req.getAccount());
		map.put("mobile", req.getMobile());
		map.put("package",req.getPackage());
		req.setSign(maiYuanService.getMYSign(map));
		return req;
		
	}
	
	
	

	

	
	
	

	
	
	




}
