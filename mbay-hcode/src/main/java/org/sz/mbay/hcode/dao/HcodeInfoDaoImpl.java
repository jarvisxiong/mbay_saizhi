package org.sz.mbay.hcode.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.hcode.bean.HcodeInfo;

@Repository
public class HcodeInfoDaoImpl extends BaseDaoImpl<HcodeInfo> implements HcodeInfoDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HcodeInfoDaoImpl.class);
	
	public HcodeInfo findHcodeInfo(String code) {
		HcodeInfo bean = null;
		try{
			bean = template.selectOne("findHcodeInfo", code);
		}catch(Exception e){
			LOGGER.error("HcodeDaoImpl findHcodeInfo方法出错,原因:数据库获取hcodeInfo信息失败,hcode:" + code);
		}
		return bean;
	}

	public List<HcodeInfo> findHcodeInfoList() {
		List<HcodeInfo> list = null;
		try{
			list = template.selectList("findHcodeInfoList");
		}catch(Exception e){
			LOGGER.error("HcodeDaoImpl findHcodeInfoList方法出错,原因:数据库获取hcodeInfoList信息失败");
		}
		return list;
	}
}