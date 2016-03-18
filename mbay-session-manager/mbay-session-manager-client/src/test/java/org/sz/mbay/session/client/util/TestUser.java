package org.sz.mbay.session.client.util;

import java.io.Serializable;

public class TestUser implements Serializable {
	
	private static final long serialVersionUID = -6033665720960707779L;
	
	private String name;
	
	private int age;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}
