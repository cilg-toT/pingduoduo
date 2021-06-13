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
import com.mysql.jdbc.Util;
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
	static String PDD_SecretKey = "E47D6A7C2E8EDDE0F303919B694DEA67";
	
	
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
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("account", maiYuanService.getAccount());
		map.put("mobile", mobile);
		map.put("package",packageRespon.getPackage());
		form.add("sign", maiYuanService.getMYSign(map));
		
		
		try {
			String resp = Request.Post(maiYuanService.getLiuLiangUrl()).bodyForm(form.build()).execute().returnContent().asString();
			PostOrderDataFlowReq data =  new Gson().fromJson(resp, PostOrderDataFlowReq.class);
			
			Date date = new Date();
			data.setMoreData(outOrderNo, packageRespon.getPrice(), mobile, packageRespon.getPackage(), date.getTime()+"");
			if(data.getCode().equals("0")){
			}
			return data ;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String notifyUrl(String outOrderNo, String orderNo, String status) {
		// TODO Auto-generated method stub
		status = status.equals("4")?"SUCCESS":"FAIL";
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("order_sn", outOrderNo);
		map.put("outer_order_sn", orderNo);
		map.put("status", status);
		map.put("mall_id", "637786");
		Date date = new Date();
		String timestamp = date.getTime()/1000+"";
		timestamp = "1506674045";
		map.put("type", "pdd.virtual.mobile.charge.notify");
		map.put("data_type", "JSON");
		map.put("timestamp", timestamp);
		
		String sign = PDD_Md5(map,com.zyinf.util.Util.getMaiYuanConfig("PDD_SecretKey"));
		
		String PDD_Url="http://open.yangkeduo.com/api/router?type=pdd.virtual.mobile.charge.notify&data_type=JSON&timestamp="+timestamp
				+"&order_sn="+outOrderNo
				+"&outer_order_sn="+orderNo
				+"&status="+status
				+"&mall_id=637786&sign="+sign;
		
		try {
			String resp = Request.Post(PDD_Url).execute().returnContent().asString();
			return resp;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAIL";
	}
	
	private String PDD_Md5(Map<String, String> params, String lastKey) {
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
				sb.append(key  + value);
			else
				sb.append( key  + value);
		}
		String s = lastKey+sb.toString()+lastKey;
		return PDD_MD5(s);
	}
	
	private String PDD_MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	
	
	

	
	
	




}
