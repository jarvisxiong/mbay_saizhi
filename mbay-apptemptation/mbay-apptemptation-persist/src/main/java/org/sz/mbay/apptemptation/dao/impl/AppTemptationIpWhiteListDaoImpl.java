package org.sz.mbay.apptemptation.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.apptemptation.bean.AppTemptationIpWhiteList;
import org.sz.mbay.apptemptation.dao.AppTemptationIpWhiteListDao;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;

@Repository
public class AppTemptationIpWhiteListDaoImpl
		extends BaseDaoImpl<AppTemptationIpWhiteList>
		implements AppTemptationIpWhiteListDao {
		
	@Override
	public AppTemptationIpWhiteList findByUserNumber(String usernumber) {
		return template.selectOne("AppTemptationIpWhiteList.findByUserNumber",
				usernumber);
	}
	
	@Override
	public int updateByIdSelective(AppTemptationIpWhiteList bean) {
		return template.update("AppTemptationIpWhiteList.updateByIdSelective",
				bean);
	}
	
	@Override
	public int create(AppTemptationIpWhiteList bean) {
		bean.setId(PKgen.getInstance().nextPK());
		return template.insert("AppTemptationIpWhiteList.create", bean);
	}
	
	@Override
	public boolean checkExistsByUserNumber(String userNumber) {
		return template.selectOne(
				"AppTemptationIpWhiteList.checkExistsByUserNumber", userNumber);
	}
	
	@Override
	public int deleteById(Long id) {
		return template.delete("AppTemptationIpWhiteList.deleteById", id);
	}
	
	@Override
	public AppTemptationIpWhiteList findById(Long id) {
		return template.selectOne("AppTemptationIpWhiteList.findById", id);
	}
	
}
