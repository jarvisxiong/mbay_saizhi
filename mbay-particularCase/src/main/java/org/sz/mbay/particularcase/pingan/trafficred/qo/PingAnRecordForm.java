package org.sz.mbay.particularcase.pingan.trafficred.qo;

import java.io.Serializable;

import org.sz.mbay.base.qo.BaseForm;
import org.sz.mbay.particularcase.pingan.trafficred.enums.MbayStatus;

/**
 * 平安
 * 
 * @author user
 *		
 */
@SuppressWarnings("serial")
public class PingAnRecordForm extends BaseForm implements Serializable {
	
	// 手机号
	private String mobile;
	// 自身处理状态
	private MbayStatus mbayStatus;
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public MbayStatus getMbayStatus() {
		return mbayStatus;
	}
	
	public void setMbayStatus(MbayStatus mbayStatus) {
		this.mbayStatus = mbayStatus;
	}
	
}
