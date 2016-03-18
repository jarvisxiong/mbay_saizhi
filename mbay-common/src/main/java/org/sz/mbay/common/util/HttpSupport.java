package org.sz.mbay.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpSupport
 * 
 * @author jerry
 */
public class HttpSupport {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpSupport.class);
			
	public enum ReqMethod {
		GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
	}
	
	public enum ReqProtocol {
		HTTP, HTTPS;
	}
	
	private HttpRequestBase requestBase;
	
	private HttpClient client;
	
	public HttpRequestBase getRequestBase() {
		return requestBase;
	}
	
	public HttpClient getClient() {
		return client;
	}
	
	/**
	 * 请求（默认：GET，参数需跟在url后面）
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static HttpSupport build(String url)
			throws HttpException, IOException {
		return build(url, ReqMethod.GET, null);
	}
	
	/**
	 * 请求（默认：POST）
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static HttpSupport build(
			String url,
			Map<String, String> params)
					throws HttpException, IOException {
		return build(url, ReqMethod.POST, params);
	}
	
	/**
	 * 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param method
	 *            请求类型
	 * @param params
	 *            请求参数
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static HttpSupport build(
			String url,
			ReqMethod method,
			Map<String, String> params)
					throws HttpException, IOException {
		return build(url, method, -1, -1, params);
	}
	
	/**
	 * 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param method
	 *            请求类型
	 * @param connTimeout
	 *            链接超时时间
	 * @param socketTimeout
	 *            socket超时时间
	 * @param params
	 *            请求参数
	 * @param httpClient
	 *            请求代理
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static HttpSupport build(
			String url,
			ReqMethod method,
			int connTimeout,
			int socketTimeout,
			Map<String, String> params) throws HttpException, IOException {
		if (url == null) {
			throw new HttpException("url cannot be null");
		}
		
		HttpSupport instance = new HttpSupport();
		
		if (url.startsWith("http")) {
			instance.client = getClient(ReqProtocol.HTTP);
		} else if (url.startsWith("https")) {
			instance.client = getClient(ReqProtocol.HTTPS);
		} else {
			throw new HttpException(
					"url should start with 'http' or 'https'");
		}
		
		if (method == null) {
			method = ReqMethod.POST;
		}
		switch (method) {
			case GET:
				// 设置参数
				if (params != null && !params.isEmpty()) {
					if (url.indexOf("?") == -1) {
						url += "?";
					}
					String key, value;
					for (Entry<String, String> entry : params.entrySet()) {
						key = entry.getKey();
						if (key == null) {
							continue;
						}
						value = entry.getValue();
						if (value == null) {
							value = "";
						}
						url += "&" + URLEncoder.encode(key, "UTF-8") +
								"=" + URLEncoder.encode(value, "UTF-8");
					}
				}
				instance.requestBase = new HttpGet(url);
				break;
			default:
			case POST:
				instance.requestBase = new HttpPost(url);
				
				// 设置参数
				if (params != null && !params.isEmpty()) {
					List<NameValuePair> tmp = new ArrayList<NameValuePair>();
					for (Entry<String, String> entry : params.entrySet()) {
						tmp.add(new BasicNameValuePair(entry.getKey(), entry
								.getValue()));
					}
					((HttpPost) instance.requestBase)
							.setEntity(new UrlEncodedFormEntity(tmp, "UTF-8"));
				}
				break;
		}
		
		if (connTimeout > 0 && socketTimeout > 0) {
			RequestConfig requestConfig = buildRequestConfig(connTimeout,
					socketTimeout, 5000);
			instance.requestBase.setConfig(requestConfig);
		}
		
		return instance;
	}
	
	/**
	 * 请求（仅限POST,允许文件）
	 * 
	 * @param url
	 *            请求地址
	 * @param fields
	 *            普通参数
	 * @param files
	 *            文件参数
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 * @throws Exception
	 */
	public static HttpSupport build(
			String url,
			Map<String, String> fields,
			Map<String, InputStream> files) throws IOException, HttpException {
		HttpSupport instance = new HttpSupport();
		instance.client = HttpClients.createDefault();
		
		instance.requestBase = new HttpPost(url);
		MultipartEntity reqEntity = new MultipartEntity();
		
		// 普通参数
		if (fields != null && !fields.isEmpty()) {
			for (Entry<String, String> entry : fields.entrySet()) {
				reqEntity.addPart(entry.getKey(),
						new StringBody(entry.getValue()));
			}
		}
		// 文本参数
		if (files != null && !files.isEmpty()) {
			for (Entry<String, InputStream> entry : files.entrySet()) {
				reqEntity.addPart(entry.getKey(),
						new InputStreamBody(entry.getValue(), entry.getKey()));
			}
		}
		((HttpPost) instance.requestBase).setEntity(reqEntity);
		return instance;
	}
	
	/**
	 * 执行连接
	 * 
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String connect() throws HttpException, IOException {
		CloseableHttpResponse response = null;
		try {
			response = (CloseableHttpResponse) client.execute(requestBase);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				String result = entity != null ? EntityUtils.toString(entity)
						: null;
				return result;
			} else {
				throw new HttpException(
						"Unexpected response status: " + status,
						String.valueOf(status));
			}
		} finally {
			if (response != null) {
				response.close();
			}
			if (client != null) {
				((Closeable) client).close();
			}
		}
	}
	
	/**
	 * 添加请求头
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public HttpSupport addHeader(String key, String value) {
		requestBase.addHeader(key, value);
		return this;
	}
	
	/**
	 * 添加请求头
	 * 
	 * @param headers
	 * @return
	 */
	public HttpSupport addHeaders(Map<String, String> headers) {
		if (headers != null) {
			headers.forEach((name, value) -> {
				requestBase.addHeader(name, value);
			});
		}
		return this;
	}
	
	/*
	 * 获取请求代理
	 */
	private static HttpClient getClient(ReqProtocol type) {
		HttpClient client = null;
		if (ReqProtocol.HTTP == type) {
			client = HttpClients.createDefault();
		} else if (ReqProtocol.HTTPS == type) {
			try {
				SSLContextBuilder builder = new SSLContextBuilder();
				builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
						builder.build());
				client = HttpClients.custom().setSSLSocketFactory(sslsf)
						.build();
			} catch (Exception e) {
				LOGGER.error("get https client error:{}", e.getMessage());
				client = HttpClients.createDefault();
			}
		}
		return client;
	}
	
	/*
	 * 请求配置
	 */
	private static RequestConfig buildRequestConfig(
			final int connectTimeOut,
			final int socketTimeout,
			final int connectionRequestTimeout) {
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeOut)
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setStaleConnectionCheckEnabled(true).build();
		return requestConfig;
	}
	
}
