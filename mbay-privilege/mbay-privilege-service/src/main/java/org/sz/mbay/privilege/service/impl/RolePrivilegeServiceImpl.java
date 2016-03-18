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
import org.sz.mbay.privilege.bean.RolePrivilege;
import org.sz.mbay.privilege.dao.RolePrivilegeDao;
import org.sz.mbay.privilege.service.RolePrivilegeService;

@Service("RolePrivilegeService")
public class RolePrivilegeServiceImpl extends BaseServiceImpl implements RolePrivilegeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RolePrivilegeServiceImpl.class);
	
	@Autowired
	RolePrivilegeDao rolePrivilegeDao;

	@Override
	public List<RolePrivilege> findAllRolePrivilege(String roleName, String privilegeName, PageInfo pageInfo) {
		List<RolePrivilege> list = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleName", roleName);
		map.put("privilegeName", privilegeName);
		try {
			list = rolePrivilegeDao.findList(map, pageInfo, RolePrivilege.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("RolePrivilegeServiceImpl findAllRolePrivilege Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public RolePrivilege findRolePrivilege(int id) {
		RolePrivilege bean = null;
		try {
			bean = rolePrivilegeDao.getBean(id, RolePrivilege.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("RolePrivilegeServiceImpl findRolePrivilege Error", e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public List<RolePrivilege> findAllRolePrivilegeByRoleId(int roleId) {
		List<RolePrivilege> list = null;
		try {
			list = rolePrivilegeDao.findList(roleId, null, "RolePrivilegeByRoleId");
		} catch (Exception e) {
			LOGGER.error("RolePrivilegeServiceImpl findAllRolePrivilegeByRoleId Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public ExecuteResult add(RolePrivilege bean) {
        if(isExistRolePrivilege(bean.getRoleId(), bean.getPrivilegeId())){
        	LOGGER.error("已存在这条记录,请勿重复提交!");
        	return ExecuteResult.failExecute;
        }
		try{
			rolePrivilegeDao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("RolePrivilegeServiceImpl add Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult update(RolePrivilege bean) {
		try{
			rolePrivilegeDao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("RolePrivilegeServiceImpl update Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult delete(int id) {
		try{
			rolePrivilegeDao.deleteBean(id);
		} catch (Exception e) {;
			LOGGER.error("RolePrivilegeServiceImpl delete Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public boolean isExistRolePrivilege(int roleId, int privilegeId) {
		return rolePrivilegeDao.countRolePrivilegeSize(roleId, privilegeId) > 0 ? true : false;
	}
	
}