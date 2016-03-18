package org.sz.mbay.privilege.service;

import java.util.List;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.BuserRole;

/**
 * 用户-角色对应关系Service
 * @author frank.zong
 *
 */
public interface BuserRoleService {
	
	/**
	 * 根据用户名称、角色名称模糊查询用户-角色对应关系
	 * 
	 * @param userName
	 * @param roleName
	 * @return
	 */
	public List<BuserRole> findAllBuserRole(String userName, String roleName, PageInfo pageInfo);
	
	/**
	 * 根据id查询用户-角色对应关系
	 * 
	 * @param id
	 * @return
	 */
	public BuserRole findBuserRole(int id);
	
	/**
	 * 根据userId查询用户-角色对应关系
	 * 
	 * @param id
	 * @return
	 */
	public List<BuserRole> findAllBuserRoleByUserId(int userId);
	
	/**
	 * 添加用户-角色对应关系
	 * @param bean
	 */
	public ExecuteResult add(BuserRole bean);
	
	/**
	 * 更新用户-角色对应关系
	 * @param bean
	 */
	public ExecuteResult update(BuserRole bean);
	
	/**
	 * 删除用户-角色对应关系
	 * @param bean
	 */
	public ExecuteResult delete(int id);
	
	/**
	 * 根据用户、角色查询对应关系是否已经存在
	 * 
	 * @param userId
	 * @return roleId
	 */
	public boolean isExistBuserRole(int userId, int roleId);
}
