package org.sz.mbay.duiba.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.duiba.bean.DuiBaMall;

public interface DuiBaMallDao extends BaseDao<DuiBaMall> {
	
    //查询-单条
    public DuiBaMall findOne(int id);
    
}
