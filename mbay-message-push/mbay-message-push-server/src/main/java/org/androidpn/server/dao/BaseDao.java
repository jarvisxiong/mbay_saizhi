package org.androidpn.server.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 基本dao辅助层
 * 
 * @author jerry
 */
public interface BaseDao<T> {
	
	public T get(Serializable id);
	
	public T saveOrUpdate(T bean);
	
	public void delete(Serializable id);
	
	public void delete(T bean);
	
	public boolean exist(Serializable id);
	
	public T selectOne(String query, Object param);
	
	public T selectOne(String query, Object[] params);
	
	public List<T> selectList(String query);
	
	public List<T> selectList(String query, Object params);
	
	public List<T> selectList(String query, Object[] params);
	
	public <E> List<E> selectList(String query, Class<E> clazz);
	
	public <E> List<E> selectList(String query, Object param, Class<E> clazz);
	
	public <E> List<E> selectList(String query, Object[] params, Class<E> clazz);
}
