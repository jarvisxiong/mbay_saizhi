package org.sz.mbay.hcode.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.hcode.dao.HcodeInfoDao;

@Service("HcodeInfoService")
public class HcodeInfoServiceImpl implements HcodeInfoService{
	
	@Autowired
	private HcodeInfoDao hcodedao;

	public HcodeInfo getHcodeInfo(String code) {
		return hcodedao.findHcodeInfo(code);
	}

	public List<HcodeInfo> getHcodeInfoList() {
		return hcodedao.findHcodeInfoList();
	}
	
}