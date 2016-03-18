package org.sz.mbay.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;

public class BaseDaoImpl<T> implements BaseDao<T> {
	
	public static final String FIND = "find";
	
	public static final String FIND_ALL = "findAll";
	
	public static final String CREATE = "create";
	
	public static final String DELETE = "delete";
	
	public static final String UPDATE = "update";
	
	public static final String COUNT = "count";
	
	@Autowired
	protected SqlSessionTemplate template;
	private Class<T> tclass;
	
	// private SqlExecutor executor;
	// public Map<String, Object> params = null;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		// 获取当前Dao 类类型
		try {
			ParameterizedType type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			tclass = (Class<T>) type.getActualTypeArguments()[0];
		} catch (Exception e) {}
		// 实例化 SqlMapClientTemplate
		// /template = super.getSqlMapClientTemplate();
	}
	
	@Override
	public T getBean(Serializable id) {
		return template.<T> selectOne(FIND + tclass.getSimpleName(), id);
	}
	
	@Override
	public T getBean(Serializable id, String stateName) {
		return template.<T> selectOne(FIND + stateName, id);
	}
	
	@Override
	public <E> E getBean(Serializable id, Class<E> tclass) {
		return template.<E> selectOne(FIND + tclass.getSimpleName(), id);
		
	}
	
	@Override
	public <E> List<E> findList(Object bean, PageInfo pageinfo) {
		
		return this.findList(bean.getClass(), bean, pageinfo);
		
	}
	
	@Override
	public <E> List<E> findList(Class<?> clas, Object bean, PageInfo pageinfo) {
		return this.findList(bean, pageinfo, clas.getSimpleName());
	}
	
	@Override
	public <E> List<E> findList(Object bean, PageInfo pageinfo, String satename) {
		if (pageinfo != null) {
			if (pageinfo.getTotalrow() == 0) {
				int total = this.countBean(bean, satename);
				pageinfo.setTotalrow(total);
			}
			RowBounds rowBounds = new RowBounds(pageinfo.getOffset(),
					pageinfo.getLimit());
			return template.selectList(FIND_ALL + satename, bean, rowBounds);
			
		} else {
			return template.selectList(FIND_ALL + satename, bean);
		}
	}
	
	@Override
	public <E> List<E> findList(String satename, PageInfo pageinfo) {
		if (pageinfo != null) {
			if (pageinfo.getTotalrow() == 0) {
				int total = this.countBean(satename);
				pageinfo.setTotalrow(total);
			}
			return template.selectList(FIND_ALL + satename, null,
					new RowBounds(pageinfo.getOffset(), pageinfo.getLimit()));
		} else {
			return template.selectList(FIND_ALL + satename);
		}
	}
	
	/**
	 * @param <T>
	 * @param t
	 * @param object
	 * @return 创建实例并返回
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public <E> E createBean(Serializable bean) {
		template.insert(CREATE + bean.getClass().getSimpleName(), bean);
		return (E) bean;
	}
	
	/**
	 * 删除
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int deleteBean(Serializable id) throws SQLException {
		return template.delete(DELETE + tclass.getSimpleName(), id);
	}
	
	/**
	 * 修改
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int updateBean(Serializable object) {
		return template.update(UPDATE + object.getClass().getSimpleName(),
				object);
	}
	
	@Override
	public <E> int countBean(E bean) {
		return this.countBean(bean, bean.getClass().getSimpleName());
		
	}
	
	@Override
	public <E> int countBean(E bean, String statname) {
		return (Integer) this.template.selectOne(COUNT + statname, bean);
		
	}
	
	@Override
	public <E> int countBean(String statname) {
		return (Integer) this.template.selectOne(COUNT + statname);
		
	}
	
	@Override
	public <E> void batchCreate(final List<E> beanlist) {
		
	}
	
	@Override
	public <E> void batchUpdate(final List<E> beanlist) {
		
	}
	
	@Override
	public <E> int deleteBeanByParam(E bean) throws SQLException {
		return this.template.delete(DELETE + bean.getClass().getSimpleName(),
				bean);
	}
}
