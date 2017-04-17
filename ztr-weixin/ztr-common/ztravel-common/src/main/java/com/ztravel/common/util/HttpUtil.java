package com.ztravel.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.logger.core.TZMarkers;



public class HttpUtil {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

	public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

	/**
	 * http get请求获取html
	 * @param option_url
	 * @return
	 */
	public static String httpGet(String option_url,String charset){
        URL url;
        String html = "";

		try {
			url = new URL(option_url);
			URLConnection conn = url.openConnection();
			InputStreamReader is = new InputStreamReader(conn.getInputStream(),charset == null ? "gb2312": charset);
			html = IOUtils.toString(is);
		} catch (MalformedURLException e) {
			LOG.error(TZMarkers.p2, "根据ip解析地址异常", e);
		} catch (IOException e) {
			LOG.error(TZMarkers.p2, "根据ip解析地址IO异常", e);
		}

        return html;
	}

	/**
	 * http post xml
	 * @param option_url
	 * @param inputXML
	 * @return
	 */
	public static String postXml(String option_url, String inputXML) {
		InputStream in ;
		StringEntity entity = new StringEntity(inputXML, ContentType.create("text/xml", Consts.UTF_8));
		entity.setChunked(true);
		HttpPost httppost = new HttpPost(option_url);

		httppost.setEntity(entity);

		HttpClient client = HttpClients.createDefault();
		String result = "";

		try {
			HttpResponse response = client.execute(httppost);

			in = response.getEntity().getContent();
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
				result = IOUtils.toString(in);
			}
			else {
				LOG.info(IOUtils.toString(in));
			}
		} catch (ClientProtocolException e) {
			LOG.error(TZMarkers.p2, "http post ClientProtocolException", e);
		} catch (IOException e) {
			LOG.error(TZMarkers.p2, "http post IOException", e);
		}
		return result;
	}

	public static String doPost(URL url, String contentType, String input) throws IOException {
		return doPost(url, contentType, ((InputStream) (new ByteArrayInputStream(input.getBytes()))));
	}

	public static String doPost(URL url, String contentType, InputStream stuffToPost) throws IOException {
		HttpURLConnection conn;
		OutputStream ostr;
		conn = (HttpURLConnection) url.openConnection();
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection secureConn = (HttpsURLConnection) conn;
			secureConn.setHostnameVerifier(new HostnameVerifier() {

				public boolean verify(String host, SSLSession sslSession) {
					return true;
				}

			});
		}
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", contentType);
		ostr = null;
		ostr = conn.getOutputStream();
		copy(stuffToPost, ostr);
		if (ostr != null)
			ostr.close();
		BufferedReader reader;
		if (ostr != null)
			ostr.close();
		conn.connect();
		reader = null;
		String s;
		try {
			int rc = conn.getResponseCode();
			if (rc != 200)
				throw new IOException((new StringBuilder("code ")).append(rc).append(" '").append(conn.getResponseMessage()).append("'")
						.toString());
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()), 512);
			String response = toString(reader);
			s = response;
			return s;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	private static String toString(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			sb.append(line).append('\n');
		return sb.toString();
	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte buf[] = new byte[4096];
		for (int len = 0; (len = in.read(buf)) != -1;)
			out.write(buf, 0, len);

	}



	public static void main(String[] args) {
//		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=9QgAtvX7CJjVcdY5UBEQdwle98MYjGkzuEClWdTgpM85au1fnOlcuWvftCVlBuGQeJMqT2esLo2vfsHbqMmHIoBoayz8vN3p7KKzApG3jCs";
//		JSONObject json  = JSON.parseObject(httpGet(url,"utf-8"));
//		System.out.println(json.get("country"));
//		Map<String, List<Button>> map = new HashMap<String, List<Button>>() ;
//		List<Button> bs = new ArrayList<Button>() ;
//		Button button = new Button() ;

//		button.setType("view");
//		button.setName("zhifu");
//		String rurl = UriEncoder.encode("http://wx.extdev.travelzen.cn/ztravel-front-weixin/weixin/orderPay/selectPayType?orderId=1508141514323503") ;
//		String uuurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WechatAccountHolder.APP_ID
//				+ "&redirect_uri=" + rurl
//				+ "&response_type=code&scope=snsapi_base&state=1#wechat_redirect" ;
//		button.setUrl(uuurl);
//		bs.add(button) ;
//		map.put("button", bs) ;
//		System.out.println(uuurl) ;
//		String xml = JSON.toJSON(map).toString() ;
//		System.out.println(postXml(url,xml)) ;
	}

}
