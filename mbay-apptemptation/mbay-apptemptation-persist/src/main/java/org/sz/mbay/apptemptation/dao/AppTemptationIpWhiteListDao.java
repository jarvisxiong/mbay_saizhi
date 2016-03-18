package org.sz.mbay.apptemptation.dao;

import org.sz.mbay.apptemptation.bean.AppTemptationIpWhiteList;
import org.sz.mbay.base.dao.BaseDao;

public interface AppTemptationIpWhiteListDao
		extends BaseDao<AppTemptationIpWhiteList> {
		
	AppTemptationIpWhiteList findByUserNumber(String usernumber);
	
	int updateByIdSelective(AppTemptationIpWhiteList bean);
	
	int create(AppTemptationIpWhiteList bean);

	boolean checkExistsByUserNumber(String userNumber);

	int deleteById(Long id);

	AppTemptationIpWhiteList findById(Long id);
	
}
