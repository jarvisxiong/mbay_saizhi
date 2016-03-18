package org.sz.mbay.privilege.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.privilege.bean.Buser;
import org.sz.mbay.privilege.dao.BuserDao;
import org.sz.mbay.privilege.service.BuserService;

@Service("BuserService")
public class BuserServiceImpl extends BaseServiceImpl implements BuserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuserServiceImpl.class);
	
	@Autowired
	BuserDao buserDao;

	@Override
	public List<Buser> findAllBuser(String name, PageInfo pageInfo) {
		List<Buser> list = null;
		try {
			list = buserDao.findList(name, pageInfo, Buser.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("BuserServiceImpl findAllBuser Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public Buser findBuser(int id) {
		Buser bean = null;
		try {
			bean = buserDao.getBean(id, Buser.class.getSimpleName());
		} catch (Exception e) {
			LOGGER.error("BuserServiceImpl findBuser Error", e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public Buser findBuser(String name) {
		Buser bean = null;
		try {
			bean = buserDao.getBean(name, "BuserByName");
		} catch (Exception e) {
			LOGGER.error("BuserServiceImpl findBuser Error", e.fillInStackTrace());
		}
		return bean;
	}

	@Override
	public ExecuteResult add(Buser bean) {
		try{
			buserDao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("BuserServiceImpl add Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult update(Buser bean) {
		try{
			buserDao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("BuserServiceImpl update Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}

	@Override
	public ExecuteResult delete(int id) {
		try{
			buserDao.deleteBean(id);
		} catch (Exception e) {;
			LOGGER.error("BuserServiceImpl delete Error", e.fillInStackTrace());
			return ExecuteResult.failExecute;
		}
		return ExecuteResult.successExecute;
	}
	
}