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
import com.zyinf.bean.dataFlow.MYNotifyDataFlowReq;
import com.zyinf.bean.telFee.MYNotifyReq;
import com.zyinf.exception.InvalidParamException;
import com.zyinf.service.dataFlow.DataFlowService;
import com.zyinf.service.dataFlow.DataFlowServiceImpl;


public class DataFLowNotifyUrlServlet extends MyServlet {
	private static final long serialVersionUID = 4048847830736501287L;
	
	static Logger log = Logger.getLogger(DataFLowNotifyUrlServlet.class.getName());
	
	final String appKey1 = "2185dba9b2ed3e9e811f1f63636caa11";
	final String account1 = "hzhxtest";
	
	static DataFlowService dataFlowService = new DataFlowServiceImpl();

	
	public String doWork(HttpServletRequest request) throws Exception {
		try {
			
			String TaskID = getStringParam(request, "TaskID", null);
			if(TaskID == null)
				throw new InvalidParamException("TaskID is null?");	
			String mobile = getStringParam(request, "Mobile", null);
			if(mobile == null)
				throw new InvalidParamException("Mobile is null?");	
			String Status = getStringParam(request, "Status", null);
			if(Status == null)
				throw new InvalidParamException("Status is null?");	
			
			String ReportTime =  getStringParam(request, "ReportTime", null);
			if(ReportTime == null)
				throw new InvalidParamException("ReportTime is null?");	

			String ReportCode = getStringParam(request, "ReportCode", null);
			if(ReportCode == null)
				throw new InvalidParamException("ReportCode is null?");	
			
			String OutTradeNo = getStringParam(request, "OutTradeNo", null);
			if(OutTradeNo == null)
				throw new InvalidParamException("OutTradeNo is null?");	
			
			String Sign = getStringParam(request, "Sign", null);
			if(Sign == null)
				throw new InvalidParamException("Sign is null?");	
			
			MYNotifyDataFlowReq req = new MYNotifyDataFlowReq();
			req.setTaskID(TaskID);
			req.setMobile(mobile);
			req.setStatus(Status);
			req.setReportTime(ReportTime);
			req.setReportCode(ReportCode);
			req.setOutTradeNo(OutTradeNo);
			req.setSign(Sign);
			
			System.out.println("迈远回调数据"+gson().toJson(req));
			
			return dataFlowService.requstPDDDataFlowNotify(req);
			
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
