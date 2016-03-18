package org.sz.mbay.traffic.util.http;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: HttpSupport
 * @Description: http通信帮助类
 * @modified <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * @date 2014年11月27日 上午11:11:44
 * 
 */
public class HttpSupport {

	static Logger logger = LoggerFactory.getLogger(HttpSupport.class);

	public static final String CONNECT_REFUSED = "connect refused";
	public static final String NO_RESPONSE_MSG = "no response message";

	public static String connectViaGet(String url) throws Exception {
		return connectViaGet(url, -1, -1);
	}

	public static String connectViaGet(String url, int connTimeout,
			int socketTimeout) throws Exception {
		ProtocolType protocolType = parseProtocolType(url);
		HttpClient httpclient = getClient(protocolType);
		return httpGet(httpclient, url, connTimeout, socketTimeout);
	}

	public static String connectViaPost(String url, String postParam)
			throws Exception {
		return connectViaPost(url, postParam, -1, -1);
	}

	public static String connectViaPost(String url, String param,
			int connTimeout, int socketTimeout) throws Exception {
		ProtocolType protocolType = parseProtocolType(url);
		HttpClient httpclient = getClient(protocolType);
		return httpPost(httpclient, url, param, connTimeout, socketTimeout);
	}
	///{"appkey":"saizhikeji","appsecret":"5cde56193d3626df1e84a255e6f5c434","sign":"ec5d4fb6d42a173011db018adb952c252f6941e8"}

	private static String httpPost(HttpClient httpClient, String url,
			String httpParam, int connTimeout, int socketTimeout)
			throws Exception {
		// 定义请求对象，此对象为HttpGet/HttpPost/HttpDelete等其他请求方式封装类的基类，so可以用多态
		String responseBody = null;
		HttpPost httpPost = new HttpPost(url);
		StringEntity input = new StringEntity(httpParam);
		httpPost.setEntity(input);
		if (connTimeout > 0 && socketTimeout > 0) {
			// 用参数构建requestConfig
			RequestConfig requestConfig = buildRequestConfig(connTimeout,
					socketTimeout, 5000);
			httpPost.setConfig(requestConfig);
		}
		logger.info("Executing request " + httpPost.getRequestLine());

		try {
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					System.out.println(status);
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			responseBody = httpClient.execute(httpPost, responseHandler);
			if (responseBody == null) {
				responseBody = HttpSupport.NO_RESPONSE_MSG;
			}
			/*
			 * } /*catch (HttpHostConnectException e) { responseBody =
			 * HttpSupport.CONNECT_REFUSED; ((Closeable) httpClient).close(); //
			 * // 运行时异常处理 logger.error("doRequest:连接被拒绝:",
			 * e.fillInStackTrace());
			 */
		} finally {
			if (httpClient != null) {
				((Closeable) httpClient).close();
			}
		}

		return responseBody;
	}

	/**
	 * 
	 * @param httpClient
	 * @param url
	 *            请求url
	 * @param method
	 *            请求方式
	 * @param socketTimeout
	 *            小于0 表示不设置
	 * @param connTimeout
	 *            小于0 表示不设置
	 * @return
	 * @throws Exception
	 */
	public static String httpGet(HttpClient httpClient, String url,
			int connTimeout, int socketTimeout) throws Exception {
		// 定义请求对象，此对象为HttpGet/HttpPost/HttpDelete等其他请求方式封装类的基类，so可以用多态
		String responseBody = null;
		HttpRequestBase requestBase = new HttpGet(url);
		if (connTimeout > 0 && socketTimeout > 0) {
			// 用参数构建requestConfig
			RequestConfig requestConfig = buildRequestConfig(connTimeout,
					socketTimeout, 5000);
			// 客户端设置请求配置
			requestBase.setConfig(requestConfig);
		}
		logger.info("Executing request " + requestBase.getRequestLine());

		try {
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					System.out.println(status);
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			responseBody = httpClient.execute(requestBase, responseHandler);
			if (responseBody == null) {
				responseBody = HttpSupport.NO_RESPONSE_MSG;
			}
			/*
			 * } /*catch (HttpHostConnectException e) { responseBody =
			 * HttpSupport.CONNECT_REFUSED; ((Closeable) httpClient).close(); //
			 * // 运行时异常处理 logger.error("doRequest:连接被拒绝:",
			 * e.fillInStackTrace());
			 */
		} finally {
			if (httpClient != null) {
				((Closeable) httpClient).close();
			}
		}

		return responseBody;
	}

	/**
	 * 
	 * @param type
	 *            协议类型
	 * @return
	 * @throws Exception
	 */
	public static HttpClient getClient(ProtocolType type) throws Exception {
		HttpClient client = null;
		if (ProtocolType.HTTP == type) {
			client = HttpClients.createDefault();
		} else if (ProtocolType.HTTPS == type) {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					builder.build());
			client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		}
		return client;
	}

	/**
	 * 根据Url解析并返回其协议类型
	 * 
	 * @param url
	 * @return
	 */
	private static ProtocolType parseProtocolType(String url) {
		if (url.startsWith("https")) {
			return ProtocolType.HTTPS;
		}
		return ProtocolType.HTTP;
	}

	/**
	 * 
	 * @param connectTimeOut
	 *            连接超时值
	 * @param socketTimeout
	 *            请求数据超时值
	 * @param connectionRequestTimeout
	 *            请求链接超时值
	 * @return 请求配置对象
	 */
	static RequestConfig buildRequestConfig(final int connectTimeOut,
			final int socketTimeout, int connectionRequestTimeout) {
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeOut)
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setStaleConnectionCheckEnabled(true).build();
		return requestConfig;
	}

	private enum ProtocolType {
		HTTP, HTTPS;

	}

}
