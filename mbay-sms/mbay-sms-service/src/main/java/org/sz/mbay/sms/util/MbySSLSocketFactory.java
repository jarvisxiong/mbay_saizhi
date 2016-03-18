package org.sz.mbay.sms.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MbySSLSocketFactory {

    public void http() throws ClientProtocolException, IOException,
	    NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
	SSLContextBuilder builder = new SSLContextBuilder();
	builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		builder.build());
	CloseableHttpClient httpclient = HttpClients.custom()
		.setSSLSocketFactory(sslsf).build();

	HttpGet httpGet = new HttpGet("https://some-server");
	CloseableHttpResponse response = httpclient.execute(httpGet);
	try {
	    System.out.println(response.getStatusLine());
	    HttpEntity entity = response.getEntity();
	    EntityUtils.consume(entity);
	} finally {
	    response.close();
	}
    }
}
