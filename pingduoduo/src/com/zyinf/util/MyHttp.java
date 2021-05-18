package com.zyinf.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author admin
 *
 */
public class MyHttp {
	static Logger log = Logger.getLogger(MyHttp.class.getName());
	private CloseableHttpClient httpclient = null;

	// it'll affect the behaviour of response
	public static String USER_AGENT =
		//"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/6.0)";	// Win7+IE10
		"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";	// Win10+IE11
	
	public MyHttp(String type) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		this(type, USER_AGENT, false, true);
	}
	public MyHttp(String type, boolean disableCookie, boolean disableRedirect) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		this(type, USER_AGENT, disableCookie, disableRedirect);
	}
	public MyHttp(String type, String userAgent, boolean disableCookie, boolean disableRedirect) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		HttpClientBuilder builder = null;
		if ("https".equals(type)) {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			builder = HttpClients.custom()
					.setSSLSocketFactory(sslsf);
			
		} 
		else
			builder = HttpClients.custom();
		
		if(userAgent != null)
			builder.setUserAgent(userAgent);
		if(disableCookie)
			builder.disableCookieManagement();
		if(disableRedirect)
			builder.disableRedirectHandling();
		
		httpclient = builder.build();
	}

	public void close() {
		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String get(String url, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		if (headers != null && !headers.isEmpty()) {
			Set<Entry<String, String>> entries = headers.entrySet();
			Iterator<Entry<String, String>> it = entries.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		log.debug(httpGet.getRequestLine());		
		CloseableHttpResponse response = httpclient.execute(httpGet);
		//log.debug(response.getStatusLine());

		scanHttpHeaders(httpGet.getAllHeaders());
		try {
			this.headers = response.getAllHeaders();
			scanHttpHeaders(response.getAllHeaders());

			HttpEntity entity = response.getEntity();			
			return EntityUtils.toString(entity, "UTF-8");
		} 
		finally {
			response.close();
		}
	}

	public String post(String url, String jsonData, Map<String, String> headers)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			Set<Entry<String, String>> entries = headers.entrySet();
			Iterator<Entry<String, String>> it = entries.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if (jsonData != null) {
			HttpEntity entity = new StringEntity(jsonData, ContentType.create(
					"application/x-www-form-urlencoded", "utf-8"));
			httpPost.setEntity(entity);
		}
		
		log.debug(httpPost.getRequestLine());		
		CloseableHttpResponse response = httpclient.execute(httpPost);
		//log.debug(response.getStatusLine());

		scanHttpHeaders(httpPost.getAllHeaders());
		try {
			this.headers = response.getAllHeaders();
			scanHttpHeaders(response.getAllHeaders());

			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "UTF-8");
		} 
		finally {
			response.close();
		}
	}
	public String postJson(String url, String jsonData, Map<String, String> headers)
		throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			Set<Entry<String, String>> entries = headers.entrySet();
			Iterator<Entry<String, String>> it = entries.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if (jsonData != null) {
			HttpEntity entity = new StringEntity(jsonData, ContentType.create(
					"application/json", "utf-8"));
			httpPost.setEntity(entity);
		}
		
		log.debug(httpPost.getRequestLine());		
		CloseableHttpResponse response = httpclient.execute(httpPost);
		//log.debug(response.getStatusLine());

		scanHttpHeaders(httpPost.getAllHeaders());
		try {
			this.headers = response.getAllHeaders();
			scanHttpHeaders(response.getAllHeaders());
		
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "UTF-8");
		} 
		finally {
			response.close();
		}
	}

	private Header[] headers = null;
	public Header[] getHeaders() {
		return headers;
	}

	public String findKey(String key) {
		if (headers != null) {
			for (Header header : headers) {
				if (header.getName().equals(key))
					return header.getValue();

				String value = header.getValue();
				String[] splits = value.split(";");
				for (String split : splits) {
					String[] ss = split.split("=");
					String k = ss[0].trim();
					if (k.equals(key)) {
						if (ss.length == 2)
							return ss[1].trim();
						else
							return "";
					}
				}
			}
		}
		return null;
	}

	private void scanHttpHeaders(Header[] headers) {
//		if (headers != null) {
//			for (int i = 0; i < headers.length; i++) {
//				Header header = headers[i];
//				log.debug("Header[" + i + "]: " + header.getName() + "="
//						+ header.getValue());
//			}
//		}
	}

}
