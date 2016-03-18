package org.sz.mbay.duiba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.dao.DuiBaMallDao;

@Service("DuiBaMallService")
public class DuiBaMallServiceImpl implements DuiBaMallService {
	
	@Autowired
	DuiBaMallDao dao;
	
	public DuiBaMall findOne(int id) {
		return this.dao.findOne(id);
	}
	
}
