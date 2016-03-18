package org.sz.mbay.privilege.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.Role;
import org.sz.mbay.privilege.dao.RoleDao;
import org.sz.mbay.privilege.service.RoleService;

@Service("RoleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	RoleDao roleDao;

	@Override
	public List<Role> findAllRole(String name, PageInfo pageInfo) {
		List<Role> list = null;
		try {
			list = roleDao.findList(name, pageInfo, Role.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("RoleServiceImpl findAllRole Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public Role findRole(int id) {
		Role bean = null;
		try {
			bean = roleDao.getBean(id, Role.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("RoleServiceImpl findRole Error", e.fillInStackTrace());
		}
		return bean;
	}

	@Override
	public ExecuteResult add(Role bean) {
		try{
			roleDao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("RoleServiceImpl add Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult update(Role bean) {
		try{
			roleDao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("RoleServiceImpl update Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult delete(int id) {
		try{
			roleDao.deleteBean(id);
		} catch (Exception e) {;
			LOGGER.error("RoleServiceImpl delete Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}
	
}