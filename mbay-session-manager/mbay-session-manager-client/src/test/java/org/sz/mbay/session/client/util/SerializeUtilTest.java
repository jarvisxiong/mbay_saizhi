package org.sz.mbay.session.client.util;

import java.io.IOException;
import java.io.Serializable;

public class SerializeUtilTest {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		TestUser u =new TestUser();
		u.setName("xxx");
		u.setAge(3);
		
		String s = SerializeUtil.serialize(u);
		System.out.println(s);
		
		Serializable o = SerializeUtil.deserialize(s);
		TestUser uo = (TestUser) o;
		System.out.println(uo.getName() + ":" + uo.getAge());
	}
}
