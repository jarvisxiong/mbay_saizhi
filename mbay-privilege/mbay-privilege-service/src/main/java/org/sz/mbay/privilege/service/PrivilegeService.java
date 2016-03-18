package org.sz.mbay.privilege.service;

import java.util.List;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.Privilege;

/**
 * 权限Service
 * @author frank.zong
 *
 */
public interface PrivilegeService {
	
	/**
	 * 根据名称模糊查询权限
	 * 
	 * @param name
	 * @return
	 */
	public List<Privilege> findAllPrivilege(String name, PageInfo pageInfo);
	
	/**
	 * 根据id查询权限
	 * 
	 * @param id
	 * @return
	 */
	public Privilege findPrivilege(int id);
	
	/**
	 * 添加权限
	 * @param bean
	 */
	public ExecuteResult add(Privilege bean);
	
	/**
	 * 更新权限
	 * @param bean
	 */
	public ExecuteResult update(Privilege bean);
	
	/**
	 * 删除权限
	 * @param bean
	 */
	public ExecuteResult delete(int id);
}
