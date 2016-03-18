package org.sz.mbay.trafficSign.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.trafficSign.bean.TrafficSign;
import org.sz.mbay.trafficSign.dao.TrafficSignDao;
import org.sz.mbay.trafficSign.enums.TrafficSignStatus;
import org.sz.mbay.trafficSign.service.TrafficSignService;

@Service
public class TrafficSignServiceImpl extends BaseServiceImpl
		implements TrafficSignService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficSignServiceImpl.class);
			
	@Autowired
	TrafficSignDao dao;
	
	@Override
	public void add(TrafficSign bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("TrafficSignService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public void del(int id) {
		try {
			this.dao.deleteBean(id);
		} catch (Exception e) {
			LOGGER.error("TrafficSignService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public TrafficSign findTrafficSignByUserNumber(String usernumber) {
		TrafficSign bean = null;
		try {
			bean = this.dao.findTrafficSignByUserNumber(usernumber);
		} catch (Exception e) {
			LOGGER.error("TrafficSignService add Error", e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public List<TrafficSign> findAllTrafficSign(PageInfo pageinfo,
			String number) {
		List<TrafficSign> list = null;
		try {
			TrafficSign bean = new TrafficSign();
			bean.setUsernumber(number);
			list = this.dao.findList(bean, pageinfo, "TrafficSign");
		} catch (Exception e) {
			LOGGER.error("TrafficSignService findAllTrafficSign Error",
					e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public TrafficSign findTrafficSignById(int id) {
		TrafficSign bean = null;
		try {
			bean = this.dao.findTrafficSignById(id);
		} catch (Exception e) {
			LOGGER.error("TrafficSignService findTrafficSignById Error",
					e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public ExecuteResult audit(int id, String reason,
			TrafficSignStatus status) {
		try {
			TrafficSign bean = this.dao.findTrafficSignById(id);
			if (reason != null && !reason.equals("")) {
				bean.setReason(reason);
			} else {
				// 加密 -> 用户编号+时间+随机数
				String pid = bean.getUsernumber() + System.currentTimeMillis()
						+ Math.random();
				bean.setPid(DigestUtils.md5Encrypt(pid));
			}
			
			bean.setStatus(status);
			this.dao.updateBean(bean);
			return new ExecuteResult(true, "审核成功");
		} catch (Exception e) {
			LOGGER.error("TrafficSignService audit Error",
					e.fillInStackTrace());
		}
		return new ExecuteResult(false, "审核失败");
	}
	
	@Override
	public void change(int id, EnableState enable) {
		TrafficSign bean = null;
		try {
			bean = this.dao.findTrafficSignById(id);
			bean.setEnable(enable);
			this.dao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("SmsTemplateService changeStatus Error",
					e.fillInStackTrace());
		}
	}
}
