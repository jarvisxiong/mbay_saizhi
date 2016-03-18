package org.sz.mbay.base.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;

/**
 * @author han.han
 * 
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * 根据主键查询实体。
	 * 
	 * @param id
	 * @return
	 */
	public T getBean(Serializable id);	
	
	/**
	 * @param id 主键
	 * @param stateName 此stateName会拼接为findstateName
	 * @return
	 */
	public T getBean(Serializable id,String stateName);
	
	/**
	 * 泛型查询实体。
	 * 
	 * @param id
	 *            主键
	 * @param clas
	 *            类型
	 * @return
	 * @throws SQLException
	 */
	public <E> E getBean(Serializable id, Class<E> clas);
	
	/**
	 * 更具查询提交返回实体集合
	 * 
	 * @param bean
	 *            查询体检bean
	 * @param pageinfo
	 *            分页
	 * @return
	 */
	public <E> List<E> findList(Object bean, PageInfo pageinfo);
	
	/**
	 * @param bean 实体bean
	 * @param pageinfo 分页信息
	 * @param statementname mybatis查询名字
	 * @return
	 */
	public <E> List<E> findList(Class<?> clas,Object bean, PageInfo pageinfo);
	
	/**
	 * @param bean 实体bean
	 * @param pageinfo 分页信息
	 * @param statementname mybatis查询名字
	 * @return
	 */
	public <E> List<E> findList(Object bean, PageInfo pageinfo,String statementname);
	
	/**
	 * @param statementname
	 * @param pageinfo
	 * @return
	 * @throws SQLException
	 */
	public <E> List<E> findList(String statementname,PageInfo pageinfo)throws SQLException;
	
	/**
	 * @param object
	 * @return
	 */
	public <E> E createBean(Serializable object);
	
	/**
	 * @param beanlist
	 * @throws SQLException
	 */
	public <E> void batchCreate(List<E> beanlist) throws SQLException;
	
	/**
	 * @param beanlist
	 * @throws SQLException
	 */
	public <E> void batchUpdate(List<E> beanlist) throws SQLException;
	
	/**
	 * @param bean
	 * @return
	 */
	public <E> int countBean(E bean);
	
	/**
	 * @param bean
	 * @param statename
	 * @return
	 */
	public <E> int countBean(E bean, String statename);
	
	/**
	 * @param statename
	 * @return
	 */
	public <E> int countBean(String statename);
	
	/**
	 * @param id实体对应的唯一标识
	 * @return
	 * @throws SQLException
	 */
	public int deleteBean(Serializable id) throws SQLException;
	
	/**
	 * @param bean 所要删除的实体
	 * @return
	 * @throws SQLException
	 */
	public <E> int deleteBeanByParam(E bean) throws SQLException;
	
	/**
	 * @param bean 所要修改的实体
	 * @return
	 * @throws SQLException
	 */
	public int updateBean(Serializable bean) throws SQLException;
	
}
