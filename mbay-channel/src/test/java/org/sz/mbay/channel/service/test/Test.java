package org.sz.mbay.channel.service.test;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;




public class Test {
	private static List<String> str=new ArrayList<String>();
	public static void main(String[] args) throws InterruptedException {
		System.out.println(DateTime.now().getMillis()/1000);
		System.out.println(System.currentTimeMillis()/1000);
		
	}
	
	public String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
	

}