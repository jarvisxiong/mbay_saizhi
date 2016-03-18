package org.sz.mbay.channel.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestHashMonkey {
	private static final String UTF_8 = "UTF-8";
	private static final String MD5 = "MD5";
	private Date timestamp;
	private String url;
	private byte[] hash;

	public RequestHashMonkey(HttpServletRequest request)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		super();
		setUrl(request.getRequestURL().toString());

		StringBuilder lotzOStuff = new StringBuilder();

		Iterator<Map.Entry<String, String[]>> it = request.getParameterMap()
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String[]> pairs = it.next();
			lotzOStuff.append(pairs.getKey());
			lotzOStuff.append(Arrays.toString(pairs.getValue()));
		}
		setHash(MessageDigest.getInstance(MD5).digest(
				lotzOStuff.toString().getBytes(UTF_8)));
	}
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}

}
