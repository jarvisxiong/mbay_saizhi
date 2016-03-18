package org.sz.mbay.session.client.util;

import java.util.Date;

import junit.framework.TestCase;

import org.sz.mbay.session.client.enums.Field;
import org.sz.mbay.session.client.manager.MySession;

public class ControllerTest extends TestCase {
	
	public void test1() {
		MySession ms = MySession.getInstance();
		ms.setField(Field.JSESSIONID, "123");
		ms.setAttribute("age", "aaa");
		ms.setAttribute("name", "1234");
	}
	
	public void test2() {
		MySession ms = MySession.getInstance();
		ms.setField(Field.JSESSIONID, "123");
		System.out.println(ms.getAttribute("name2"));
		System.out.println(ms.getAttribute("name"));
	}
	
	public void test3() {
		MySession ms = MySession.getInstance();
		ms.setField(Field.JSESSIONID, "123");
		ms.removeAttribute("name");
		System.out.println(ms.getAttribute("name"));
	}
	
	public void test4() {
		MySession ms = MySession.getInstance();
		ms.setField(Field.JSESSIONID, "12345");
		
		User user = new User();
		user.setAge(12);
		user.setBirth(new Date());
		user.setHeight(1.45);
		user.setName("zhangsss");
		//ms.setAttribute("x.x", user);
		
		User u = (User) ms.getAttribute("x.x");
		System.out.println(u);
	}
}
