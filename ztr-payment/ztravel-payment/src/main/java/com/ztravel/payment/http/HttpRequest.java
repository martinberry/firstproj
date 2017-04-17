package com.ztravel.payment.http;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztravel.payment.thread.ThreadPoolService;

/**
 * @author zuoning.shen
 *
 */
public class HttpRequest implements Callable<Object> {

	private final static Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

	private String url;
	private Map<String, String> params;
	private HttpRequestType httpRequestType;
	
	private static final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).build() ;

	public HttpRequest(String url) {
		this(url, null, HttpRequestType.GET);
	}

	public HttpRequest(String url, Map<String, String> params) {
		this(url, params, HttpRequestType.GET);
	}

	public HttpRequest(String url, Map<String, String> params, HttpRequestType httpRequestType) {
		this.setUrl(url);
		this.setParams(params);
		this.setHttpRequestType(httpRequestType);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public HttpRequestType getHttpRequestType() {
		return httpRequestType;
	}

	public void setHttpRequestType(HttpRequestType httpRequestType) {
		this.httpRequestType = httpRequestType;
	}

	/**
	 * 获取指定链接的响应信息，GET方式
	 * 
	 * @param pUrl
	 * @return
	 * @throws Exception
	 */
	private String sendGetRequest() throws Exception {
		StringBuffer lvFinalUrl = new StringBuffer(this.url);
		lvFinalUrl.append("?");
		if (null != this.params) {
			for (Entry<String, String> entry : this.params.entrySet()) {
				lvFinalUrl.append("&");
				lvFinalUrl.append(entry.getKey());
				lvFinalUrl.append("=");
				lvFinalUrl.append(entry.getValue());
			}
		}
		CloseableHttpClient lvClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build() ;
		String lvResponseString = null;
		try {
			HttpGet lvRequest = new HttpGet(lvFinalUrl.toString());
			CloseableHttpResponse response = lvClient.execute(lvRequest);
			try{
				lvResponseString = EntityUtils.toString(response.getEntity());
			}finally{
				response.close();
			}
		}finally{
			lvClient.close();
		} 
		
		return lvResponseString;
	}

	/**
	 * 获取指定链接的响应信息，POST方式
	 * 
	 * @param pUrl
	 * @param pParams
	 *            参数列表
	 * @return
	 * @throws Exception
	 */
	private String sendPostRequest() throws Exception {
		List<NameValuePair> lvParamsList = new ArrayList<NameValuePair>();
		if (null != this.params) {
			for (Entry<String, String> entry : this.params.entrySet()) {
				NameValuePair tmNameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				lvParamsList.add(tmNameValuePair);
			}
		}
		String lvResponseString = null;
		CloseableHttpClient lvClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build() ;
		HttpPost lvHttpPost = new HttpPost(this.url);
		try {
			lvHttpPost.setEntity(new UrlEncodedFormEntity(lvParamsList, "UTF-8"));
			CloseableHttpResponse response = lvClient.execute(lvHttpPost);
			try{
				lvResponseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}finally{
				response.close();
			}
		}finally{
			lvClient.close();
		} 
		
		return lvResponseString;
	}

	@Override
	public String call() throws Exception {
		if (this.getHttpRequestType() == HttpRequestType.GET) {
			return sendGetRequest();
		}
		return sendPostRequest();
	}

	/**
	 * 统一提交请求接口
	 * 
	 * @param pUrl
	 * @param pParams
	 * @param pHttpRequestType
	 * @return
	 * @throws Exception
	 */
	public static String sendRequest(String pUrl, Map<String, String> pParams, HttpRequestType pHttpRequestType) throws Exception {

		long startTime = System.currentTimeMillis();
		Callable<Object> lvCallable = new HttpRequest(pUrl, pParams, pHttpRequestType);
		Future<Object> lvFuture = ThreadPoolService.getInstance().submit(lvCallable);
		String lvResponse = null;

		lvResponse = (String) lvFuture.get();

		long endTime = System.currentTimeMillis();

		long last = endTime - startTime;

		LOGGER.info("Http请求地址:" + pUrl + "&" + pParams + "\nHttp请求开始时间：" + sdf.format(new Date(startTime)) + "\nHttp请求结束时间：" + sdf.format(new Date(endTime)) + "\n耗时：" + last
				/ 1000 + "秒" + last % 1000 + "毫秒");

		return lvResponse;
	}

	/**
	 * 统一提交请求接口，GET方式
	 * 
	 * @param pUrl
	 * @return
	 * @throws Exception
	 */
	public static String sendRequest(String pUrl, Map<String, String> pParams) throws Exception {
		return sendRequest(pUrl, pParams, HttpRequestType.GET);
	}
}
