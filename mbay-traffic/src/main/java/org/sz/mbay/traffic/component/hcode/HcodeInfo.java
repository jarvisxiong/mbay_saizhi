package org.sz.mbay.traffic.component.hcode;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HcodeInfo implements Serializable {
	
	private String mobile;
	/**号码归属地**/
	private int provcode;
	
	/**号码运营商**/
	private int operator;
	
	private String attribution;

	public String getAttribution() {
		String operatorstr="";
		switch(this.operator){
		case 1:operatorstr="移动"; break;
		case 2:operatorstr="联通"; break;
		case 3:operatorstr="电信"; break;
		}
		return attribution+operatorstr;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public int getProvcode() {
		return provcode;
	}

	public void setProvcode(int provcode) {
		this.provcode = provcode;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

}
