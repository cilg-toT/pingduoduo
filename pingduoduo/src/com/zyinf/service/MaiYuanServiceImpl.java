package com.zyinf.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zyinf.bean.GetPackageResp_Package;
import com.zyinf.bean.PostOrderDataFlowReq;
import com.zyinf.bean.PostPackageRep;
import com.zyinf.service.MaiYuanService;

public class MaiYuanServiceImpl implements MaiYuanService {
	static Logger log = Logger.getLogger(MaiYuanServiceImpl.class.getName());
	
	//packages 数据存储文件路径
	static String filePath = "";
	static String account = "";
	static String key = "";
	
	static String PACKAGES_KEY = "PACKAGES_KEY";
	
	public void init() throws Exception {
	}
	
	public void destroy() throws Exception {
	}

	
	
	@Override
	public String getAccount() {
		// TODO Auto-generated method stub
		return account;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}
	

	@Override
	public String getSign(String mobile, String packageStr) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("mobile", mobile);
		map.put("account", account);
		map.put("package", packageStr);
		
		String sign = appMd5(map,key);
		return sign;
	}
	
	@Override
	public GetPackageResp_Package getPackageRespon(String prodNo) {
		// TODO Auto-generated method stub
		GetPackageResp_Package packA = new GetPackageResp_Package();
		Properties pro = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream("a.properties");
			pro.load(in);
			in.close();
			String jsonStr = pro.getProperty(PACKAGES_KEY);
			GetPackageResp_Package[] array =  new Gson().fromJson(jsonStr, PostPackageRep.class).Packages;
			for(GetPackageResp_Package pack:array){
				if( pack.getPackage() == prodNo){
					return pack;
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packA;
	}
	
	@Override
	public GetPackageResp_Package getPackageRespon(String resType,
			String dataFloat, String expireDay) {
		// TODO Auto-generated method stub
		GetPackageResp_Package packA = new GetPackageResp_Package();
		
		return packA;
	}
	
	private String appMd5(Map<String, String> params, String appKey) {
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
	
	private String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       
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
