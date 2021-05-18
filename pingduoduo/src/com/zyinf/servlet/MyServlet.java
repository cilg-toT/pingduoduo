package com.zyinf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zyinf.exception.InvalidParamException;
import com.zyinf.service.MyService;
import com.zyinf.service.MyServiceImpl;
import com.zyinf.util.Util;


public abstract class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 3671318355309213116L;
	static Logger log = Logger.getLogger(MyServlet.class);

	public abstract String doWork(HttpServletRequest request) throws Exception;

	public Gson gson() {
		return new GsonBuilder()  
		  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
		  .create();  
	}

	public MyService myService;
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();         
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);                          
        myService = (MyServiceImpl)ctx.getBean("myService");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info(request.getRequestURI() + ": " + getParamsString(request)); 

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		
		long ticks = System.currentTimeMillis();
		
		String resStr = null;
		try {
			resStr = doWork(request);
			response.setStatus(HttpStatus.SC_OK);
			log.info(request.getRequestURI() 
					+ "(" + (System.currentTimeMillis() - ticks) + " ms): " + resStr); 			
		}
		catch(Exception e) {
			resStr = "{\"error\":\"" + e.getMessage() + "\"}";
			log.error(request.getRequestURI() 
					+ "(" + (System.currentTimeMillis() - ticks) + " ms): " + resStr); 			
		}
		PrintWriter out = response.getWriter();
		out.println(resStr);
		out.flush();
		out.close();
	}

	@SuppressWarnings("unchecked")
	public String getParamsString(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
	    Enumeration<String> paramNames = request.getParameterNames();  
	    while (paramNames.hasMoreElements()) {  
	    	String paramName = (String) paramNames.nextElement();  
	    	String paramValue = request.getParameter(paramName);
	    	if(sb.length() == 0)
	    		sb.append(paramName + "=" + paramValue);
	    	else
	    		sb.append("&" + paramName + "=" + paramValue);
	    }
	    if(sb.length() == 0)
	    	return "void";
	    else
	    	return sb.toString();
	}
	public String getStringParam(HttpServletRequest request, String name, String def) {
		String r = def;
		String value = request.getParameter(name);
		if(value != null) {
			r = value.trim();
		}
		return r;
	}
	public int getIntParam(HttpServletRequest request, String name, int def) throws InvalidParamException {
		int r = def;
		String value = request.getParameter(name);
		if(value != null) {
			try {
				r = Integer.parseInt(value.trim());
			}
			catch(NumberFormatException e) {
				throw new InvalidParamException("'" + name + "'(int) is '" + value + "'?");
			}
		}
		return r;
	}
	public double getDoubleParam(HttpServletRequest request, String name, double def) throws InvalidParamException {
		double r = def;
		String value = request.getParameter(name);
		if(value != null) {
			try {
				r = Double.parseDouble(value.trim());
			}
			catch(NumberFormatException e) {
				throw new InvalidParamException("'" + name + "'(double) is '" + value + "'?");
			}
		}
		return r;
	}
	public Date getDateParam(HttpServletRequest request, String name, Date def) 
		throws InvalidParamException {
		Date r = def;
		String value = request.getParameter(name);
		if(value != null) {
			try {
				r = Util.stringToDate(value.trim());
			} 
			catch (ParseException e) {
				throw new InvalidParamException("'" + name + "'(date) is '" + value + "'?");
			}
		}
		return r;
	}
	public Date getTimeParam(HttpServletRequest request, String name, Date def) 
		throws InvalidParamException {
		Date r = def;
		String value = request.getParameter(name);
		if(value != null) {
			try {
				r = Util.stringToTime(value.trim());
			} 
			catch (ParseException e) {
				throw new InvalidParamException("'" + name + "'(time) is '" + value + "'?");
			}
		}
		return r;
	}
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

}
