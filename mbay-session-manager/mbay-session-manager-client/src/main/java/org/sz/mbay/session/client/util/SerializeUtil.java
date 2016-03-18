package org.sz.mbay.session.client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLEncoder;

public class SerializeUtil {
	
	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static String serialize(Serializable object) throws IOException {
		ByteArrayOutputStream out = null;
		ObjectOutputStream oo = null;
		try {
			out = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(out);
			oo.writeObject(object);
			String serStr = out.toString("ISO-8859-1");
			serStr = URLEncoder.encode(serStr, "UTF-8");
			return serStr;
		} finally {
			if (out != null) {
				out.close();
			}
			if (oo != null) {
				oo.close();
			}
		}
	}
	
	/**
	 * 反序列化
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Serializable deserialize(String object) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = null;
		ObjectInputStream oi = null;
		try {
			String redStr = java.net.URLDecoder.decode(object, "UTF-8");
			in = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
			oi = new ObjectInputStream(in);
			return (Serializable) oi.readObject();
		} finally {
			if (in != null) {
				in.close();
			}
			if (oi != null) {
				oi.close();
			}
		}
	}
}
