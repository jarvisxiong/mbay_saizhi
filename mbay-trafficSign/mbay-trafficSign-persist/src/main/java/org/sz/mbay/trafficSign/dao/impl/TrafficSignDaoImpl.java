package org.sz.mbay.trafficSign.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficSign.bean.TrafficSign;
import org.sz.mbay.trafficSign.dao.TrafficSignDao;

@Repository
public class TrafficSignDaoImpl extends BaseDaoImpl<TrafficSign>
		implements TrafficSignDao {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficSignDaoImpl.class);
			
	@Override
	public TrafficSign findTrafficSignByUserNumber(String usernumber) {
		TrafficSign bean = null;
		try {
			bean = this.template.selectOne("findTrafficSignByUserNumber",
					usernumber);
		} catch (Exception e) {
			LOGGER.error("TrafficSignDao findTrafficSignByUserNumber Error",
					e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public TrafficSign findTrafficSignById(int id) {
		TrafficSign bean = null;
		try {
			bean = this.template.selectOne("findTrafficSignById", id);
		} catch (Exception e) {
			LOGGER.error("TrafficSignDao findTrafficSignById Error", e.fillInStackTrace());
		}
		return bean;
	}
}
