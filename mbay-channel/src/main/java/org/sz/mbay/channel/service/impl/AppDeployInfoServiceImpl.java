package org.sz.mbay.channel.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.bean.AppDeployInfo;
import org.sz.mbay.channel.dao.AppDeployInfoDao;
import org.sz.mbay.channel.service.AppDeployInfoService;

@Service(value = "appDeployInfoService")
public class AppDeployInfoServiceImpl extends BaseServiceImpl implements AppDeployInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AssetBankServiceImpl.class);
	
	@Autowired
	AppDeployInfoDao appDeployInfoDao;
	
	@Override
	public int deleteAppDeployInfo(AppDeployInfo appDeployInfo) {
		int result = 0;
		try {
			result = this.appDeployInfoDao.deleteBean(appDeployInfo);
		} catch (Exception e) {
			LOGGER.error("deleteAppDeployInfo()", e.fillInStackTrace());
		}
		return result;
	}
	
	@Override
	public AppDeployInfo createAppDeployInfo(AppDeployInfo appDeployInfo) {
		AppDeployInfo deployInfo = null;
		try {
			deployInfo = this.appDeployInfoDao.createBean(appDeployInfo);
		} catch (Exception e) {
			LOGGER.error("createAppDeployInfo", e.fillInStackTrace());
		}
		return deployInfo;
	}
	
	@Override
	public List<AppDeployInfo> findAllAppDeployInfosByName(String name) {
		List<AppDeployInfo> appDeployInfos = null;
		try {
			appDeployInfos = this.appDeployInfoDao
					.findAllAppDeployInfosByName(name);
		} catch (Exception e) {
			LOGGER.error("findAllAppDeployInfos", e.fillInStackTrace());
		}
		return appDeployInfos;
	}
}
