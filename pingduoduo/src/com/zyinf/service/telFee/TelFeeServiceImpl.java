package com.zyinf.service.telFee;

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
import com.zyinf.bean.telFee.MYNotifyReq;
import com.zyinf.bean.telFee.MYOrderTelFeeByPhoneReq;
import com.zyinf.bean.telFee.MYOrderTelFeeByPhoneRsp;
import com.zyinf.bean.telFee.PDDOrderTelFeeByPhoneReq;
import com.zyinf.bean.telFee.PDDOrderTelFeeByPhoneRsp;
import com.zyinf.bean.telFee.PDDTelFeeNotifyReq;
import com.zyinf.service.MaiYuanService;
import com.zyinf.service.MaiYuanServiceImpl;


public class TelFeeServiceImpl implements TelFeeService {
	static Logger log = Logger.getLogger(TelFeeServiceImpl.class.getName());
	
	static MaiYuanService maiYuanService = new MaiYuanServiceImpl();
	static String PDD_SecretKey = "E47D6A7C2E8EDDE0F303919B694DEA67";
	
	public void init() throws Exception {
	}
	
	public void destroy() throws Exception {
	}

	

	@Override
	public PingDuoDuoResult<PDDOrderTelFeeByPhoneRsp> requstMYTelFee(PDDOrderTelFeeByPhoneReq reqst){
		
		MYOrderTelFeeByPhoneReq req = this.getMYOrderTelFeeByPhoneReq(reqst);
		
		Form form = Form.form().add("Action", "charge").add("v", "1.1");
		form.add("action", req.getAction());
		form.add("v", req.getV());
		form.add("outTradeNo", req.getOutTradeNo());
		form.add("account",req.getAccount());
		form.add("mobile", req.getMobile());
		form.add("package", req.getPackage());
		form.add("sign", req.getSign());
		
		try {
			String resp = Request.Post(maiYuanService.getLiuLiangUrl()).bodyForm(form.build()).execute().returnContent().asString();
			MYOrderTelFeeByPhoneRsp data =  new Gson().fromJson(resp, MYOrderTelFeeByPhoneRsp.class);
			if(data.getCode().equals("0")){
			}
			PDDOrderTelFeeByPhoneRsp rsp = this.getPDDOrderTelFeeByPhoneRsp(req, data);	
			PingDuoDuoResult<PDDOrderTelFeeByPhoneRsp>result = new PingDuoDuoResult<PDDOrderTelFeeByPhoneRsp>();
			result.setResultCode("0");
			result.setResultMsg(data.getMessage());
			result.setResultData(rsp);			
			return result;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	
	
	
	public MYOrderTelFeeByPhoneReq getMYOrderTelFeeByPhoneReq(
			PDDOrderTelFeeByPhoneReq pDDOrderTelFeeByPhoneReq) {
		// TODO Auto-generated method stub
		
		MYOrderTelFeeByPhoneReq myOrderTelFeeByPhoneReq = new MYOrderTelFeeByPhoneReq();
		myOrderTelFeeByPhoneReq.setAction("charge");
		myOrderTelFeeByPhoneReq.setV("H1.1");
		myOrderTelFeeByPhoneReq.setOutTradeNo(pDDOrderTelFeeByPhoneReq.getOutOrderNo());
		myOrderTelFeeByPhoneReq.setAccount(maiYuanService.getTelFeeAccount());
		myOrderTelFeeByPhoneReq.setMobile(pDDOrderTelFeeByPhoneReq.getMobile());
		myOrderTelFeeByPhoneReq.setPackage(pDDOrderTelFeeByPhoneReq.getParPrice());
		
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("account", myOrderTelFeeByPhoneReq.getAccount());
		map.put("mobile", myOrderTelFeeByPhoneReq.getMobile());
		map.put("package",myOrderTelFeeByPhoneReq.getPackage());
		myOrderTelFeeByPhoneReq.setSign(maiYuanService.getMYTelFeeSign(map));
		
		return myOrderTelFeeByPhoneReq;
		
	}

	@Override
	public PDDOrderTelFeeByPhoneRsp getPDDOrderTelFeeByPhoneRsp(
			MYOrderTelFeeByPhoneReq mYOrderTelFeeByPhoneReq,
			MYOrderTelFeeByPhoneRsp mYOrderTelFeeByPhoneRsp) {
		// TODO Auto-generated method stub
		
		PDDOrderTelFeeByPhoneRsp pddOrderTelFeeByPhoneRsp = new PDDOrderTelFeeByPhoneRsp();
		
		pddOrderTelFeeByPhoneRsp.setStatus("ACCEPT");
		pddOrderTelFeeByPhoneRsp.setOrderNo(mYOrderTelFeeByPhoneReq.getOutTradeNo());
		pddOrderTelFeeByPhoneRsp.setOrderNo(mYOrderTelFeeByPhoneRsp.getTaskId());
		pddOrderTelFeeByPhoneRsp.setTotalFee(mYOrderTelFeeByPhoneReq.getPackage());
		pddOrderTelFeeByPhoneRsp.setMobile(mYOrderTelFeeByPhoneReq.getMobile());
		pddOrderTelFeeByPhoneRsp.setParPrice(mYOrderTelFeeByPhoneReq.getPackage());
		pddOrderTelFeeByPhoneRsp.setParValue(mYOrderTelFeeByPhoneReq.getPackage());
		Date date = new Date();
		pddOrderTelFeeByPhoneRsp.setCreateTime(date.getTime()+"");
		
		return null;
	}

	public PDDTelFeeNotifyReq getPDDTelFeeNotifyReq(MYNotifyReq myReq) {
		// TODO Auto-generated method stub
		PDDTelFeeNotifyReq req = new PDDTelFeeNotifyReq();
		
		req.setOrderNo(myReq.getTaskID());
		req.setOutOrderNo(myReq.getOutTradeNo());
		if(myReq.getStatus().equals("4")){
			req.setStatus("SUCCESS");
		}else{
			req.setStatus("Fail");
		}
		req.setSignType("md5");
		Date date = new Date();
		String timestamp = (date.getTime()/1000-60)+"";
		req.setTimeStamp(timestamp);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("order_sn",req.getOutOrderNo());
		map.put("outer_order_sn", req.getOrderNo());
		map.put("status", req.getStatus());
		map.put("mall_id", "637786");
		
		map.put("type", "pdd.virtual.mobile.charge.notify");
		map.put("data_type", "JSON");
		map.put("timestamp", req.getTimeStamp());
		
		String sign = maiYuanService.PDD_Md5(map, PDD_SecretKey);
		
		return null;
	}
	
	@Override
	public String requstPDDTelNotify(MYNotifyReq myReq) {
		// TODO Auto-generated method stub
		
		PDDTelFeeNotifyReq req = this.getPDDTelFeeNotifyReq(myReq);
		
		
		String PDD_Url="http://open.yangkeduo.com/api/router?type=pdd.virtual.mobile.charge.notify&data_type=JSON&timestamp="
				+req.getTimeStamp()
				+"&order_sn="+req.getOutOrderNo()
				+"&outer_order_sn="+req.getOrderNo()
				+"&status="+req.getStatus()
				+"&mall_id=637786&sign="+req.getSign();
		
		try {
			String resp = Request.Post(PDD_Url).execute().returnContent().asString();
			System.out.println("拼多多通知返回 "+resp);
			if(resp.contains("SUCCESS")){
				return "OK";
			}else{
				return "Fail";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAIL";
	}


	
	

	
	
	




}
