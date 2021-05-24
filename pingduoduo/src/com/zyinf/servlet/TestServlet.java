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
import com.zyinf.bean.PostOrderDataFlowReq;
import com.zyinf.bean.OrderResp;
import com.zyinf.exception.InvalidParamException;


public class TestServlet extends MyServlet {
	private static final long serialVersionUID = 4048847830736501287L;
	static Logger log = Logger.getLogger(TestServlet.class.getName());
	
	final String appKey1 = "2185dba9b2ed3e9e811f1f63636caa11";
	final String account1 = "hzhxtest";

	public String doWork(HttpServletRequest request) throws Exception {
		try {
			
			String outOrderNo = getStringParam(request, "outOrderNo", null);
			if(outOrderNo == null)
				throw new InvalidParamException("outOrderNo is null?");	
			String mobile = getStringParam(request, "mobile", null);
			if(mobile == null)
				throw new InvalidParamException("mobile is null?");	
			String notifyUrl = getStringParam(request, "notifyUrl", null);
			if(notifyUrl == null)
				throw new InvalidParamException("notifyUrl is null?");	
			
			String prodNo =  getStringParam(request, "prodNo", null);
			if(prodNo != null){
	
				PostOrderDataFlowReq orderDataFlowResp = myService.getOrderDataFlowResp(mobile, notifyUrl,outOrderNo, prodNo);
				return gson().toJson(orderDataFlowResp);
				
			}else{
				
				String resType = getStringParam(request, "resType", null);
				if(resType == null)
					throw new InvalidParamException("resType is null?");	
				String dataFloat = getStringParam(request, "dataFloat", null);
				if(dataFloat == null)
					throw new InvalidParamException("dataFloat is null?");	
				String expireDay = getStringParam(request, "expireDay", null);
				if(expireDay == null)
					throw new InvalidParamException("expireDay is null?");	
				
				PostOrderDataFlowReq orderDataFlowReq = myService.getOrderDataFlowResp(mobile, notifyUrl,outOrderNo, resType, dataFloat, expireDay);
				
				return gson().toJson(orderDataFlowReq);
			}
			
		}
		catch(InvalidParamException e) {
			OrderResp resp = new OrderResp();
			resp.setCode("1");
			resp.setMessage(e.getMessage());
			return gson().toJson(resp);
		}
	}
	
	String appMd5(Map<String, String> params, String appKey) {
		List<String> keys = new ArrayList<String>();
		Set<Entry<String, String>> aSet = params.entrySet();
		Iterator<Entry<String, String>> iter = aSet.iterator();
		while(iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			keys.add(key);
		}		
		Collections.sort(keys, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });		
		StringBuffer sb = new StringBuffer();
		for(String key : keys) {
			String value = params.get(key);
			if(sb.length() == 0)
				sb.append(key + "=" + value);
			else
				sb.append("&" + key + "=" + value);
		}
		sb.append("&key=" + appKey);
		return MD5(sb.toString());
	}

}
