package org.sz.mbay.duiba.http;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpSupport.class);
	
	public static String httpGet(String url) throws Exception {
		HttpClient httpClient = HttpClients.createDefault();
		String responseBody = null;
		HttpRequestBase requestBase = new HttpGet(url);
		try {
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						LOGGER.error("http client status -> " + status);
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			responseBody = httpClient.execute(requestBase, responseHandler);
			if (responseBody == null) {
				responseBody = "no response message";
			}
		} finally {
			if (httpClient != null) {
				((Closeable) httpClient).close();
			}
		}
		return responseBody;
	}
}