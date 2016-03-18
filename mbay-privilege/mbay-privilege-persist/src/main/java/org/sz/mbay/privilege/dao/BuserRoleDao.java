package org.sz.mbay.privilege.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.privilege.bean.BuserRole;

/**
 * 用户-角色对应关系Dao
 * @author Frank.zong
 *
 */
public interface BuserRoleDao extends BaseDao<BuserRole> {
	
	/**
	 * 根据用户、角色查询对应关系条数
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int countBuserRoleSize(int userId, int roleId);
}
