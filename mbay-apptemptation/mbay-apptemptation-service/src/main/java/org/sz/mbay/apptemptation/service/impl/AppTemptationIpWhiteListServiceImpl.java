package org.sz.mbay.apptemptation.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.apptemptation.bean.AppTemptationIpWhiteList;
import org.sz.mbay.apptemptation.dao.AppTemptationIpWhiteListDao;
import org.sz.mbay.apptemptation.qo.AppTemptationIpWhiteListQO;
import org.sz.mbay.apptemptation.service.AppTemptationIpWhiteListService;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;

@Service
public class AppTemptationIpWhiteListServiceImpl extends BaseServiceImpl
		implements AppTemptationIpWhiteListService {
		
	@Autowired
	private AppTemptationIpWhiteListDao appTemptationIpWhiteListDao;
	
	@Override
	public AppTemptationIpWhiteList findByUserNumber(String usernumber) {
		return appTemptationIpWhiteListDao.findByUserNumber(usernumber);
	}
	
	@Override
	public List<AppTemptationIpWhiteList> findPage(
			AppTemptationIpWhiteListQO qo, PageInfo pageInfo) {
		return appTemptationIpWhiteListDao.findList(qo, pageInfo,
				"AppTemptationIpWhiteList");
	}
	
	@Override
	public boolean updateByIdSelective(AppTemptationIpWhiteList bean) {
		return appTemptationIpWhiteListDao.updateByIdSelective(bean) == 1;
	}
	
	@Override
	public boolean create(AppTemptationIpWhiteList bean) {
		bean.setCreateTime(DateTime.now());
		return appTemptationIpWhiteListDao.create(bean) == 1;
	}
	
	@Override
	public boolean checkExistsByUserNumber(String userNumber) {
		return appTemptationIpWhiteListDao.checkExistsByUserNumber(userNumber);
	}

	@Override
	public boolean deleteById(Long id) {
		return appTemptationIpWhiteListDao.deleteById(id) == 1;
	}

	@Override
	public AppTemptationIpWhiteList findById(Long id) {
		return appTemptationIpWhiteListDao.findById(id);
	}
	
}
