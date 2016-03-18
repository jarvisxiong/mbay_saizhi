package org.sz.mbay.channel.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.bean.LiuMiRecord;
import org.sz.mbay.channel.bean.LiuMiTotalPrice;
import org.sz.mbay.channel.dao.LiuMiDao;
import org.sz.mbay.channel.service.LiuMiService;

@Service(value = "liuMiService")
public class LiuMiServiceImpl extends BaseServiceImpl implements LiuMiService {

	@Autowired
	LiuMiDao dao;
	
	@Override
	public boolean updateTotalPrice(double price){
		LiuMiTotalPrice bean = new LiuMiTotalPrice();
		bean.setPrice(price);
		try {
			return dao.updateBean(bean)>0?true:false;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public List<LiuMiRecord> findList(PageInfo pageinfo) {
		List<LiuMiRecord> list = null;
		try {
			list = dao.findList("LiuMiRecord", pageinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public double findLiuMiTotalPrice() {
		try {
			return dao.getBean(1).getPrice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}