package org.sz.mbay.session.client.util;

import org.sz.mbay.session.client.config.ClientConfig;

public class ConfigTest {
	
	public static void main(String[] args) {
		System.out.println(ClientConfig.getProperty(ClientConfig.TIMEOUT));
	}
}
