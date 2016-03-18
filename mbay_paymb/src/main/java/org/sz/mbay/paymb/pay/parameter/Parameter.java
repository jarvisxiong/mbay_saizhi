package org.sz.mbay.paymb.pay.parameter;

/**
 * @author han.han
 *
 */
public final class Parameter {
	

	/** 参数名称 */
	private final String name;

	// private Class<?> type;

	/** 是否必须 */
	private final boolean required;

	/** 数据合法正则 */
	private final String regExp;

	public Parameter(final String name, final boolean required, final String regExp) {
		this.name = name;
		/* this.type = type; */
		this.required = required;
		this.regExp = regExp;

	}

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}

	public String getRegExp() {
		return regExp;
	}

}
