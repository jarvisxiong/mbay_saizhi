package org.sz.mbay.privilege.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.privilege.bean.RolePrivilege;
import org.sz.mbay.privilege.dao.RolePrivilegeDao;

@Repository
public class RolePrivilegeDaoImpl extends BaseDaoImpl<RolePrivilege> implements RolePrivilegeDao {

	@Override
	public int countRolePrivilegeSize(int roleId, int privilegeId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("roleId", roleId);
		map.put("privilegeId", privilegeId);
		return this.template.selectOne("countRolePrivilegeSize", map);
	}
	
}
