package org.sz.mbay.privilege.service;

import java.util.List;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.RolePrivilege;

/**
 * 角色-权限对应关系Service
 * @author frank.zong
 *
 */
public interface RolePrivilegeService {
	
	/**
	 * 根据角色名称、权限名称模糊查询角色-权限对应关系
	 * 
	 * @param roleName
	 * @param privilegeName
	 * @return
	 */
	public List<RolePrivilege> findAllRolePrivilege(String roleName, String privilegeName, PageInfo pageInfo);
	
	/**
	 * 根据id查询角色-权限对应关系
	 * 
	 * @param id
	 * @return
	 */
	public RolePrivilege findRolePrivilege(int id);
	
	/**
	 * 根据roleId查询角色-权限对应关系
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RolePrivilege> findAllRolePrivilegeByRoleId(int roleId);
	
	/**
	 * 添加角色-权限对应关系
	 * @param bean
	 */
	public ExecuteResult add(RolePrivilege bean);
	
	/**
	 * 更新角色-权限对应关系
	 * @param bean
	 */
	public ExecuteResult update(RolePrivilege bean);
	
	/**
	 * 删除角色-权限对应关系
	 * @param bean
	 */
	public ExecuteResult delete(int id);
	
	/**
	 * 根据角色、权限查询对应关系是否已经存在
	 * 
	 * @param roleId
	 * @return privilegeId
	 */
	public boolean isExistRolePrivilege(int roleId, int privilegeId);
}
