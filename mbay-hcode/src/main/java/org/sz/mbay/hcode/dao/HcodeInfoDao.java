package org.sz.mbay.hcode.dao;

import java.util.List;
import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.hcode.bean.HcodeInfo;

public interface HcodeInfoDao extends BaseDao<HcodeInfo> {
	
	public HcodeInfo findHcodeInfo(String code);
	
	public List<HcodeInfo> findHcodeInfoList();
}
