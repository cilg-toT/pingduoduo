package com.zyinf.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zyinf.bean.GetPackageResp;
import com.zyinf.bean.PingDuoDuoResult;
import com.zyinf.bean.PostOrderDataFlowReq;
import com.zyinf.bean.OrderResp;
import com.zyinf.bean.telFee.PDDOrderTelFeeByPhoneReq;
import com.zyinf.bean.telFee.PDDOrderTelFeeByPhoneRsp;
import com.zyinf.exception.InvalidParamException;
import com.zyinf.service.telFee.TelFeeService;
import com.zyinf.service.telFee.TelFeeServiceImpl;


public class TelFeeByFhoneServlet extends MyServlet {
	private static final long serialVersionUID = 4048847830736501287L;
	static Logger log = Logger.getLogger(TelFeeByFhoneServlet.class.getName());
	static TelFeeService telFeeService = new TelFeeServiceImpl();
	
	final String appKey1 = "2185dba9b2ed3e9e811f1f63636caa11";
	final String account1 = "hzhxtest";

	public String doWork(HttpServletRequest request) throws Exception {
		try {
			
			String outOrderNo = getStringParam(request, "outOrderNo", null);
			if(outOrderNo == null)
				throw new InvalidParamException("outOrderNo is null?");	
			
			String parPrice = getStringParam(request, "parPrice", null);
			if(parPrice == null)
				throw new InvalidParamException("parPrice is null?");	
			
			String notifyUrl = getStringParam(request, "notifyUrl", null);
			if(notifyUrl == null)
				throw new InvalidParamException("notifyUrl is null?");	
			
			String mobile =  getStringParam(request, "mobile", null);
			if(mobile == null){
				throw new InvalidParamException("mobile is null?");	
			}
							
			System.out.println("订购流量->接收到拼多多的参数: mobile:"+mobile+",notifyUrl"+notifyUrl+", outOrderNo："+outOrderNo+", parPrice："+parPrice);
			PDDOrderTelFeeByPhoneReq pddOrderTelFeeByPhoneReq = new PDDOrderTelFeeByPhoneReq(); 
			pddOrderTelFeeByPhoneReq.setOutOrderNo(outOrderNo);
			pddOrderTelFeeByPhoneReq.setMobile(mobile);
			pddOrderTelFeeByPhoneReq.setAmount("1");
			pddOrderTelFeeByPhoneReq.setNotifyUrl(notifyUrl);
			pddOrderTelFeeByPhoneReq.setParPrice(parPrice);
			
			PingDuoDuoResult<PDDOrderTelFeeByPhoneRsp> result = telFeeService.requstMYTelFee(pddOrderTelFeeByPhoneReq);

			return gson().toJson(result);
			
		}
		catch(InvalidParamException e) {
			OrderResp resp = new OrderResp();
			resp.setCode("1");
			resp.setMessage(e.getMessage());
			return gson().toJson(resp);
		}
	}
	


}
