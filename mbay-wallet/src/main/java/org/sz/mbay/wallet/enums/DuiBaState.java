package org.sz.mbay.wallet.enums;

public enum DuiBaState {
	
	CREATED("初始创建"), OPERATED("已处理");
	
	private String value;
	
	private DuiBaState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public static DuiBaState valueOf(Integer state) {
		switch (state) {
			case 0: return CREATED;
			case 1: return OPERATED;
		}
		return null;
	}
}
