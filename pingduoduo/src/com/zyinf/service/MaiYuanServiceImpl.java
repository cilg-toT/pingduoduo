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
import com.google.gson.annotations.Until;
import com.zyinf.bean.GetPackageResp_Package;
import com.zyinf.bean.PostOrderDataFlowReq;
import com.zyinf.bean.PostPackageRep;
import com.zyinf.bean.PostPackageReq_Package;
import com.zyinf.service.MaiYuanService;
import com.zyinf.util.Util;
public class MaiYuanServiceImpl implements MaiYuanService {
	static Logger log = Logger.getLogger(MaiYuanServiceImpl.class.getName());
	
	//packages 数据存储文件路径
	static String filePath = "";
	static String LiuLiang_Account = "pinduoduo_sw";
	static String LiuLiang_Key = "07234354570d467881edc837a89f5685";
	static String LiuLiang_Url = "http://114.55.39.33:8080/api.aspx";
	
	static String TelFee_Account = "pinduoduo_huafei";
	static String TelFee_Key = "9ad7a7b8b0514825b4bf3b658867a44a";
	static String TelFee_Url = "http://114.55.39.33:8080/telapi.aspx";
	
	static String PACKAGES_KEY = "PACKAGES_KEY";
	
	public void init() throws Exception {
		
	}
	
	public void destroy() throws Exception {
	}
	
	

	
	@Override
	public PostPackageReq_Package getPackageRespon(String prodNo) {
		// TODO Auto-generated method stub
		PostPackageReq_Package packA = new PostPackageReq_Package();
		packA.setDATA_FLOAT(prodNo);
		packA.setName(prodNo);
		packA.setPackage(prodNo);
		packA.setPrice("10");
		return packA;
	}
	
	@Override
	public PostPackageReq_Package getPackageRespon(String resType,
			String dataFloat, String expireDay) {
		// TODO Auto-generated method stub
		PostPackageReq_Package packA = new PostPackageReq_Package();
		packA.setDATA_FLOAT(dataFloat);
		packA.setType(resType);
		
		if(resType.equals("2")){
			packA.setType("1");
		}else{
			packA.setType("1");
		}
		
		packA.setName(dataFloat);
		packA.setRES_TYPE(resType);
		packA.setPackage(dataFloat);
		packA.setPrice("10");
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
	
	@Override
	public String MD5(String s) {
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



	@Override
	public String getMYSign(Map<String, String> param) {
		// TODO Auto-generated method stub
		return appMd5(param,getKey());
	}
	@Override
	public String getMYTelFeeSign(Map<String, String> param) {
		// TODO Auto-generated method stub
		return appMd5(param,getTelFeeKey());
	}

	@Override
	public String getLiuLiangUrl() {
		// TODO Auto-generated method stub
		LiuLiang_Url = Util.getMaiYuanConfig("LiuLiang_Url");
		return LiuLiang_Url;
	}
	
	@Override
	public String getTelFeeUrl() {
		// TODO Auto-generated method stub
		TelFee_Url = Util.getMaiYuanConfig("TelFee_Url");
		return TelFee_Url;
	}
	
	
	
	@Override
	public String getAccount() {
		LiuLiang_Account =  Util.getMaiYuanConfig("LiuLiang_Account");
		return LiuLiang_Account;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		 LiuLiang_Key =  Util.getMaiYuanConfig("LiuLiang_Key");
		return LiuLiang_Key;
	}
	@Override
	public String getTelFeeAccount() {
		// TODO Auto-generated method stub
		TelFee_Account =  Util.getMaiYuanConfig("TelFee_Account");
		return TelFee_Account;
	}

	@Override
	public String getTelFeeKey() {
		// TODO Auto-generated method stub
		TelFee_Key =  Util.getMaiYuanConfig("TelFee_Key");
		return TelFee_Key;
	}
	

	@Override
	public String PDD_Md5(Map<String, String> params, String lastKey) {
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
	

	
	public String getTimeStamp(){
		
		
		return null;
		
	}
	
	




}
