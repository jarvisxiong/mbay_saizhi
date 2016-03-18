package org.sz.mbay.privilege.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.BuserRole;
import org.sz.mbay.privilege.dao.BuserRoleDao;
import org.sz.mbay.privilege.service.BuserRoleService;

@Service("BuserRoleService")
public class BuserRoleServiceImpl extends BaseServiceImpl implements BuserRoleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuserRoleServiceImpl.class);
	
	@Autowired
	BuserRoleDao buserRoleDao;

	@Override
	public List<BuserRole> findAllBuserRole(String userName, String roleName, PageInfo pageInfo) {
		List<BuserRole> list = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("roleName", roleName);
		try {
			list = buserRoleDao.findList(map, pageInfo, BuserRole.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("BuserRoleServiceImpl findAllBuserRole Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public BuserRole findBuserRole(int id) {
		BuserRole bean = null;
		try {
			bean = buserRoleDao.getBean(id, BuserRole.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("BuserRoleServiceImpl findBuserRole Error", e.fillInStackTrace());
		}
		return bean;
	}

	@Override
	public List<BuserRole> findAllBuserRoleByUserId(int userId) {
		List<BuserRole> list = null;
		try {
			list = buserRoleDao.findList(userId, null, "BuserRoleByUserId");
		} catch (Exception e) {
			LOGGER.error("BuserRoleServiceImpl findAllBuserRoleByUserId Error", e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public ExecuteResult add(BuserRole bean) {
        if(isExistBuserRole(bean.getUserId(), bean.getRoleId())){
        	LOGGER.error("已存在这条记录,请勿重复提交!");
        	return ExecuteResult.failExecute;
        }
		try{
			buserRoleDao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("BuserRoleServiceImpl add Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult update(BuserRole bean) {
		try{
			buserRoleDao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("BuserRoleServiceImpl update Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult delete(int id) {
		try{
			buserRoleDao.deleteBean(id);
		} catch (Exception e) {;
			LOGGER.error("BuserRoleServiceImpl delete Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public boolean isExistBuserRole(int userId, int roleId) {
		return buserRoleDao.countBuserRoleSize(userId, roleId) > 0 ? true : false;
	}

}