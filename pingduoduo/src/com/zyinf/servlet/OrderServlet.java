package com.zyinf.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zyinf.bean.GetPackageResp;
import com.zyinf.bean.OrderResp;
import com.zyinf.exception.InvalidParamException;


public class OrderServlet extends MyServlet {
	private static final long serialVersionUID = 4048847830736501287L;
	static Logger log = Logger.getLogger(OrderServlet.class.getName());
	
	final String appKey1 = "2185dba9b2ed3e9e811f1f63636caa11";
	final String account1 = "hzhxtest";

	/**
	 * http://114.55.39.33:8080/api.aspx?action=charge&v=1.1&account=chenwhtest&mobile=13705711892&package=10&sign=0c29d043bb150f61bc447096dca911ab
	 * {"Code":"0","Message":"","TaskID":2102754}
	 * 
	 * http://114.55.39.33:8080/api.aspx?action=getPackage&v=1.1&account=chenwhtest&type=0&sign=dfd2b92b32b5ca1240b21c517534e312
	 * {
	 * 	"Code":"0",
	 * 	"Message":"OK",
	 * 	"Packages":[
	 * 		{
	 *  		"Type":"1",
	 *  		"Package":"10",
	 * 	 		"Name":"10M",
	 *  		"Price":"3.00"
	 *  	},
	 *  	...
	 *  ]
	 * } 
	 */
	public String doWork(HttpServletRequest request) throws Exception {
		try {
			String v = getStringParam(request, "v", null);
			if(v == null)
				throw new InvalidParamException("v is null?");	
			String action = getStringParam(request, "action", null);
			if(action == null)
				throw new InvalidParamException("action is null?");	
			String account = getStringParam(request, "account", null);
			if(account == null)
				throw new InvalidParamException("account is null?");	
			String sign = getStringParam(request, "sign", null);
			if(sign == null)
				throw new InvalidParamException("sign is null?");	
			
			String type = getStringParam(request, "type", null);
			if(type == null)
				throw new InvalidParamException("type is null?");	
			if(!type.equals("0") && !type.equals("1")) 
				throw new InvalidParamException("type is " + type + "?");	
			
			// 验证sign
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", account);
			params.put("type", type);
			
			String md5 = appMd5(params, appKey1);
			if(!sign.equals(md5))
				throw new InvalidParamException("md5 error?");	
		
			GetPackageResp resp = myService.getPackages(type);
			return gson().toJson(resp);
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
