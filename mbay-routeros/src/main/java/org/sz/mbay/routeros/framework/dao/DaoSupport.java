package org.sz.mbay.routeros.framework.dao;

import java.io.Serializable;
import java.util.List;

import org.sz.mbay.routeros.entity.PageInfo;

/**
 * @author han.han
 * 
 * @param <T>
 */
public interface DaoSupport<T> {
	
	public T findBean(Serializable id);
	
	public <E> E findBean(Serializable id, Class<E> clas);
	
	public <E> List<E> findList(Object bean, PageInfo pageinfo);
	
	public <E> List<E> findList(Object bean, PageInfo pageinfo, String statementname);
	
	public <E> List<E> findList(PageInfo pageinfo, String statementname);
	
	public <E> E createBean(Serializable object);
	
	public <E> int countBean(E bean);
	
	public <E> int countBean(E bean, String statename);
	
	public <E> int countBean(String statename);
	
	public int deleteBean(Serializable id);
	
	public <E> int deleteBeanByParam(E bean);
	
	public int updateBean(Serializable object);
	
}
