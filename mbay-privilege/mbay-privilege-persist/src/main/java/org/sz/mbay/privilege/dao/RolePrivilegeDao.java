package org.sz.mbay.privilege.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.privilege.bean.RolePrivilege;

/**
 * 角色-权限对应关系Dao
 * @author Frank.zong
 *
 */
public interface RolePrivilegeDao extends BaseDao<RolePrivilege> {
	
	/**
	 * 根据角色、权限查询对应关系条数
	 * @param roleId
	 * @param privilegeId
	 * @return
	 */
	public int countRolePrivilegeSize(int roleId, int privilegeId);
}
