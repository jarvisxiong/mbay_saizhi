package org.sz.mbay.privilege.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.privilege.bean.BuserRole;
import org.sz.mbay.privilege.dao.BuserRoleDao;

@Repository
public class BuserRoleDaoImpl extends BaseDaoImpl<BuserRole> implements BuserRoleDao {

	@Override
	public int countBuserRoleSize(int userId, int roleId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("roleId", roleId);
		return this.template.selectOne("countBuserRoleSize", map);
	}
	
}
