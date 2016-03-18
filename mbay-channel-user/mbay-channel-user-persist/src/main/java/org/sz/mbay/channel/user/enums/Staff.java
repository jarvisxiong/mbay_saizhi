package org.sz.mbay.channel.user.enums;

public enum Staff {
	
	NON(""),
	LESSFORTYNINE("1-49人"),
	LESSNINETYNINE("50-99人"),
	LESSFOURHUNDREDANDNINETYNINE("100-499人"),
	LESSNINEHUNDREDANDNINETYNINE("500-999人"),
	MORETHANONETHOUSAND("1000人以上");
	
	private String value;
	
	private Staff(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
