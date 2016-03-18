package org.androidpn.server.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.androidpn.server.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 * dao基本辅助类
 * 
 * @author jerry
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	private Class<T> tclass;
	
	@Autowired
	private HibernateTemplate template;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			tclass = (Class<T>) type.getActualTypeArguments()[0];
		} catch (Exception e) {
		}
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return template;
	}
	
	@Override
	public T get(Serializable id) {
		return (T) template.get(tclass, id);
	}
	
	@Override
	public T saveOrUpdate(T bean) {
		template.saveOrUpdate(bean);
		template.flush();
		return bean;
	}
	
	@Override
	public void delete(Serializable id) {
		template.delete(get(id));
	}
	
	@Override
	public void delete(T bean) {
		template.delete(bean);
	}
	
	@Override
	public boolean exist(Serializable id) {
		T bean = (T) template.get(tclass, id);
		return bean != null;
	}
	
	@Override
	public T selectOne(String query, Object param) {
		return selectOne(query, new Object[] { param });
	}
	
	@Override
	public T selectOne(String query, Object[] params) {
		List<T> datas = selectList(query, params, tclass);
		return (datas == null || datas.isEmpty()) ? null : (T) datas.get(0);
	}
	
	@Override
	public List<T> selectList(String query) {
		return selectList(query, null, tclass);
	}
	
	@Override
	public List<T> selectList(String query, Object params) {
		return selectList(query, new Object[] { params }, tclass);
	}
	
	@Override
	public List<T> selectList(String query, Object[] params) {
		return selectList(query, params, tclass);
	}
	
	@Override
	public <E> List<E> selectList(String query, Class<E> clazz) {
		return selectList(query, null, clazz);
	}
	
	@Override
	public <E> List<E> selectList(String query, Object param, Class<E> clazz) {
		return selectList(query, new Object[] { param }, clazz);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> selectList(String query, Object[] params, Class<E> clazz) {
		List<E> datas;
		if (params == null || params.length == 0) {
			datas = (List<E>) template.find(query);
		} else {
			datas = (List<E>) template.find(query, params);
		}
		return datas;
	}

}
