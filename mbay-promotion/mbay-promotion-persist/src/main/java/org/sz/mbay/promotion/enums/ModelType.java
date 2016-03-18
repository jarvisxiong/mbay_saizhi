package org.sz.mbay.promotion.enums;

public enum ModelType {

	NON(""), TEMPLATE("编辑模式"), ADVANCED("开发者模式");

	private String value;

	private ModelType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
