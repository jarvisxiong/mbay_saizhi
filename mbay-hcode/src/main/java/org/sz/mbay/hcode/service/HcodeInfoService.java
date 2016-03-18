package org.sz.mbay.hcode.service;

import java.util.List;

import org.sz.mbay.hcode.bean.HcodeInfo;

public interface HcodeInfoService {
	
	public HcodeInfo getHcodeInfo(String code);
	
	public List<HcodeInfo> getHcodeInfoList();
}
