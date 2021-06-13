package com.zyinf.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Util {
	static Logger log = Logger.getLogger(Util.class.getName());
	/**
	 * 时间
	 */
	
	public static String getMaiYuanConfig(String key) {
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath(); 
		String[] split = path.split("/");
		split[split.length -1] = "conf";
		path = "";
		for (int i = 0; i < split.length; i++) {
			path = path+ split[i]+"/";
		}
		
		try {
			properties.load(new FileInputStream(path+"maiYuan.properties"));
			String string =   properties.get(key).toString();
			return string;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date now() {
		return Calendar.getInstance().getTime();
	}
	public static Date afterDay(Date time, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	public static Date afterMinute(Date time, int mins) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, mins);
		return cal.getTime();
	}
	public static int diffDays(Date d1, Date d2) {
		return (int)((d1.getTime() - d2.getTime()) / (1000 * 3600 * 24)); 
	}	

	public static Date stringToDate(String date) throws ParseException {
		synchronized (lockObj) {
			return DayFormat.parse(date);
		}
	}
	public static String dateToString(Date date) {
		synchronized (lockObj) {
			return DayFormat.format(date);
		}
	}
	public static Date stringToTime(String date) throws ParseException {
		synchronized (lockObj) {
			return TimeFormat.parse(date);
		}
	}
	public static String timeToString(Date date) {
		synchronized (lockObj) {
			return TimeFormat.format(date);
		}
	}
	public static Date truncDate(Date date) throws ParseException {
		return Util.stringToDate(Util.dateToString(date));
	}
	static final Object lockObj = new Object();
	static SimpleDateFormat DayFormat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	static DecimalFormat df = new DecimalFormat("#.00");
	public static String formatDouble(double d) {
		return df.format(d);
	}
	public static double fineDouble(double d) {
		return Double.parseDouble(df.format(d));
	}
	
	public static List<Integer> CommaStrToIntList(String mStr) {
		List<Integer> aList = new ArrayList<Integer>();
		// split(): "1,,2"返回"1","","2"；"1,2,"返回"1","2"
		String[] array = mStr.split(",");
		for(int i = 0; i < array.length; i++) {
			String item = array[i];
			if(item.equals(""))
				continue;
			aList.add(Integer.parseInt(item));
		}
		return aList;
	}		

	public static <T> List<List<T>> divideToGroups(List<T> aList, int size) {
		List<T> result = null;
		List<List<T>> results = new ArrayList<List<T>>(); 
		for(int i = 0; i < aList.size(); i++) {
			if(i % size == 0) {
				result = new ArrayList<T>();
				results.add(result);
			}
			result.add(aList.get(i));
		}
		return results;
	}
	// URL 
    static String toHexString(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex;
	}
    public static String encodeUrl(String url) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < url.length(); i++) {
			String s = url.substring(i, i + 1);
			byte[] bytes = s.getBytes("utf8");
			
			// 中文至少为两个字节，一个字节不可能为中文
			if (bytes.length == 1) {
				if (bytes[0] == ' ')
					sb.append("%20");
				else
					sb.append(s);
			} else {
				for (int j = 0; j < bytes.length; j++) {
					sb.append("%" + toHexString(bytes[j]));
				}
			}
		}
		return sb.toString();
	}

}