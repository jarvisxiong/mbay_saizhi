package org.sz.mbay.session.client.util;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	
	private static final long serialVersionUID = -5767104337325821175L;
	
	private String name;
	
	private Integer age;
	
	private double height;
	
	private Date birth;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public Date getBirth() {
		return birth;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	@Override
	public String toString() {
		return "User [" + (name != null ? "name=" + name + ", " : "") + (age != null ? "age=" + age + ", " : "") + "height=" + height + ", " + (birth != null ? "birth=" + birth : "") + "]";
	}
}
