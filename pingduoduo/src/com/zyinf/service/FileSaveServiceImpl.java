package com.zyinf.service;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class FileSaveServiceImpl  {
	static Logger log = Logger.getLogger(FileSaveServiceImpl.class.getName());
	
	//packages 数据存储文件路径
	private String filePath = "/WEB-INF/conf/maiYuan.properties";
	
	public String getFilePath() {
		String t=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"META-INF/maiYuan.properties";
		System.out.println("t---"+t);
		filePath = t;
		return t;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	static Properties pro = new Properties();
	
	 public synchronized void save(String key ,String value){
		try {
			FileInputStream in = new FileInputStream(this.getFilePath());
			pro.load(in);
			

			pro.setProperty(key, value);
			FileOutputStream oFile = new FileOutputStream(this.getFilePath(), true);//true表示追加打开
			pro.store(oFile, "");
			in.close();
			oFile.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		
	}
	 public synchronized void remove(String key){
		try {
			FileInputStream in = new FileInputStream(this.getFilePath());
			pro.load(in);
			pro.remove(key);
			FileOutputStream oFile = new FileOutputStream(this.getFilePath(), true);//true表示追加打开
			pro.store(oFile, "..");
			in.close();
			oFile.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		
	}
	
	 public  String get(String key ){
		try {
			FileInputStream in = new FileInputStream(this.getFilePath());
			pro.load(in);
			in.close();
			return pro.getProperty(key);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		return null;
	}
	

	
	




}
