package org.sz.mbay.privilege.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.Privilege;
import org.sz.mbay.privilege.dao.PrivilegeDao;
import org.sz.mbay.privilege.service.PrivilegeService;

@Service("PrivilegeService")
public class PrivilegeServiceImpl extends BaseServiceImpl implements PrivilegeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegeServiceImpl.class);
	
	@Autowired
	PrivilegeDao privilegeDao;

	@Override
	public List<Privilege> findAllPrivilege(String name, PageInfo pageInfo) {
		List<Privilege> list = null;
		try {
			list = privilegeDao.findList(name, pageInfo, Privilege.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("PrivilegeServiceImpl findAllPrivilege Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public Privilege findPrivilege(int id) {
		Privilege bean = null;
		try {
			bean = privilegeDao.getBean(id, Privilege.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("PrivilegeServiceImpl findPrivilege Error", e.fillInStackTrace());
		}
		return bean;
	}

	@Override
	public ExecuteResult add(Privilege bean) {
		try{
			privilegeDao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("PrivilegeServiceImpl add Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult update(Privilege bean) {
		try{
			privilegeDao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("PrivilegeServiceImpl update Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult delete(int id) {
		try{
			privilegeDao.deleteBean(id);
		} catch (Exception e) {;
			LOGGER.error("PrivilegeServiceImpl delete Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}
	
}