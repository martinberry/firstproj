package com.ztravel.paygate.web.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	private static class SSLHandler implements X509TrustManager, HostnameVerifier {

		public void checkClientTrusted(X509Certificate ax509certificate[], String s) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate ax509certificate[], String s) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}

		private SSLHandler() {
		}

		SSLHandler(SSLHandler sslhandler) {
			this();
		}
	}

	public HttpUtil() {
	}

	public static HostnameVerifier getVerifier() {
		return simpleVerifier;
	}

	public static synchronized SSLSocketFactory getSSLSF() throws Exception {
		if (sslFactory != null) {
			return sslFactory;
		} else {
			SSLContext sc = SSLContext.getInstance("SSLv3");
			sc.init(null, new TrustManager[] { simpleVerifier }, null);
			sslFactory = sc.getSocketFactory();
			return sslFactory;
		}
	}

	private static URLConnection createRequest(String strUrl, String strMethod) throws Exception {
		URL url = new URL(strUrl);
		URLConnection conn = url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
			httpsConn.setRequestMethod(strMethod);
			httpsConn.setSSLSocketFactory(getSSLSF());
			httpsConn.setHostnameVerifier(getVerifier());
		} else if (conn instanceof HttpURLConnection) {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod(strMethod);
		}
		return conn;
	}

	public static URL getRedirectedUrl(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(false);
		conn.addRequestProperty("Connection", "close");
		int rc = conn.getResponseCode();
		if (rc != 302 && rc != 301)
			throw new IOException((new StringBuilder("code ")).append(rc).append(" '").append(conn.getResponseMessage()).append("'")
					.toString());
		String location = conn.getHeaderField("Location");
		if (location == null)
			throw new IOException("No 'Location' header found");
		else
			return new URL(location);
	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte buf[] = new byte[4096];
		for (int len = 0; (len = in.read(buf)) != -1;)
			out.write(buf, 0, len);

	}

	public static String doFormPost(URL url, InputStream stuffToPost) throws IOException {
		return doPost(url, "application/x-www-form-urlencoded", stuffToPost);
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

	public static String doPost(URL url, String contentType, String input) throws IOException {
		return doPost(url, contentType, ((InputStream) (new ByteArrayInputStream(input.getBytes()))));
	}

	public static String doGet(URL url) throws IOException {
		HttpURLConnection conn;
		BufferedReader reader;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		reader = null;
		String s;
		try {
			int rc = conn.getResponseCode();
			if (rc != RESPONSE_OK)
				throw new IOException((new StringBuilder("code ")).append(rc).append(" '").append(conn.getResponseMessage()).append("'")
						.toString());
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 512);
			s = toString(reader);
			return s;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	private static String buildUrl(String baseurl, Map params) throws IOException {
		if (params.isEmpty())
			return baseurl;
		else
			return (new StringBuilder(String.valueOf(baseurl))).append("?").append(buildQuery(params)).toString();
	}

	public static String doFormPost(URL url, Map params) throws IOException {
		String qry = buildQuery(params);
		return doPost(url, APPLICATION_X_WWW_FORM_URLENCODED, qry);
	}

	public static String buildQuery(Map params) {
		StringBuilder sb = null;
		for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object v = params.get(key);
			String value = v == null ? "" : v.toString();
			if (sb == null) {
				sb = new StringBuilder();
				sb.append(escape(key)).append('=').append(escape(value));
			} else {
				sb.append("&").append(escape(key)).append('=').append(escape(value));
			}
		}

		return sb.toString();
	}

	public static String doGet(String baseurl, Map params) throws IOException {
		return doGet(new URL(buildUrl(baseurl, params)));
	}

	private static String escape(String s) {
		return URLEncoder.encode(s);
	}

	public static String getXML(URL url) throws IOException {
		HttpURLConnection conn;
		BufferedReader reader;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		reader = null;
		String s;
		try {
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			s = toString(reader);
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

	private static String readString(BufferedReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			sb.append(line);
		return sb.toString();
	}

	private static Map getParams(BufferedReader reader) throws IOException {
		Map params = new HashMap();
		String line;
		while ((line = reader.readLine()) != null) {
			int eq = line.indexOf('=');
			if (eq > 0) {
				String key = line.substring(0, eq);
				String value = line.substring(eq + 1);
				params.put(key, value);
			}
		}
		return params;
	}

	public static String sendPostRequest(String urlPath, String msg) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(msg);
		byte data[] = sb.toString().getBytes();
		URLConnection conn = null;
		conn = createRequest(urlPath, "POST");
		conn.setRequestProperty("Keep-alive", "false");
		conn.setUseCaches(false);
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty("Content-Type", APPLICATION_X_WWW_FORM_URLENCODED);
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(data);
		outStream.flush();
		outStream.close();
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 512);
		return toString(reader);
	}

	private static final int DEFAULT_BUFFER_SIZE = 4096;
	private static final int REDIRECT_RESPONSE_CODE = 302;
	private static final int MOVED_PERMANENTLY_RESPONSE_CODE = 301;
	public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	private static final int RESPONSE_OK = 200;
	protected static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	private static final SSLHandler simpleVerifier = new SSLHandler(null);
	private static SSLSocketFactory sslFactory;

}
