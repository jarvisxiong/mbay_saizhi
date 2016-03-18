package org.sz.mbay.privilege.service;

import java.util.List;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.Buser;

/**
 * 用户Service
 * @author frank.zong
 *
 */
public interface BuserService {
	
	/**
	 * 根据名称模糊查询用户
	 * 
	 * @param name
	 * @return
	 */
	public List<Buser> findAllBuser(String name, PageInfo pageInfo);
	
	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 * @return
	 */
	public Buser findBuser(int id);
	
	/**
	 * 根据用户名查询用户
	 * 
	 * @param id
	 * @return
	 */
	public Buser findBuser(String name);
	
	/**
	 * 添加用户
	 * @param bean
	 */
	public ExecuteResult add(Buser bean);
	
	/**
	 * 更新用户
	 * @param bean
	 */
	public ExecuteResult update(Buser bean);
	
	/**
	 * 删除用户
	 * @param bean
	 */
	public ExecuteResult delete(int id);
}
