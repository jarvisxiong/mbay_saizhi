package org.sz.mbay.routeros.framework.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sz.mbay.routeros.entity.PageInfo;

@Repository("DaoSupportImpl")
public class DaoSupportImpl<T> implements DaoSupport<T> {
	
	@Autowired
	protected SqlSessionTemplate template;
	
	private Class<T> tclass;
	
	@SuppressWarnings("unchecked")
	public DaoSupportImpl() {
		// 获取当前Dao 类类型
		try {
			ParameterizedType type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			tclass = (Class<T>) type.getActualTypeArguments()[0];
		} catch (Exception e) {
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findBean(Serializable id) {
		return (T) template.selectOne("find" + tclass.getSimpleName(), id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E findBean(Serializable id, Class<E> tclass) {
		return (E) template.selectOne("find" + tclass.getSimpleName(), id);
	}
	
	@Override
	public <E> List<E> findList(Object bean, PageInfo pageinfo) {
		return this.findList(bean, pageinfo, bean.getClass().getSimpleName());
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
			return template.selectList("findAll" + satename, bean, rowBounds);
		} else {
			return template.selectList("findAll" + satename, bean);
		}
	}
	
	@Override
	public <E> List<E> findList(PageInfo pageinfo, String satename) {
		if (pageinfo != null) {
			if (pageinfo.getTotalrow() == 0) {
				int total = this.countBean(satename);
				pageinfo.setTotalrow(total);
			}
			return template.selectList("findAll" + satename, new RowBounds(
					pageinfo.getOffset(), pageinfo.getLimit()));
		} else {
			return template.selectList("findAll" + satename);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E> E createBean(Serializable bean) {
		template.insert("create" + bean.getClass().getSimpleName(), bean);
		return (E) bean;
	}
	
	@Override
	public int deleteBean(Serializable id) {
		return template.delete("delete" + tclass.getSimpleName(), id);
	}
	
	@Override
	public int updateBean(Serializable object) {
		return template.update("update" + object.getClass().getSimpleName(),
				object);
	}
	
	@Override
	public <E> int countBean(E bean) {
		return this.countBean(bean, bean.getClass().getSimpleName());
	}
	
	@Override
	public <E> int countBean(E bean, String statname) {
		return (Integer) this.template.selectOne("count" + statname, bean);
	}
	
	@Override
	public <E> int countBean(String statname) {
		return (Integer) this.template.selectOne("count" + statname);
	}
	
	@Override
	public <E> int deleteBeanByParam(E bean) {
		return this.template.delete("delete" + bean.getClass().getSimpleName(), bean);
	}
}
