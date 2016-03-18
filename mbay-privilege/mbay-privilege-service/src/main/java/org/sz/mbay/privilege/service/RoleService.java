package org.sz.mbay.privilege.service;

import java.util.List;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.Role;

/**
 * 角色Service
 * @author frank.zong
 *
 */
public interface RoleService {
	
	/**
	 * 根据名称模糊查询角色
	 * 
	 * @param name
	 * @return
	 */
	public List<Role> findAllRole(String name, PageInfo pageInfo);
	
	/**
	 * 根据id查询角色
	 * 
	 * @param id
	 * @return
	 */
	public Role findRole(int id);
	
	/**
	 * 添加角色
	 * @param bean
	 */
	public ExecuteResult add(Role bean);
	
	/**
	 * 更新角色
	 * @param bean
	 */
	public ExecuteResult update(Role bean);
	
	/**
	 * 删除角色
	 * @param bean
	 */
	public ExecuteResult delete(int id);
}
