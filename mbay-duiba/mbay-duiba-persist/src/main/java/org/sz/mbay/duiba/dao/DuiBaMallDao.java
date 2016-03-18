package org.sz.mbay.duiba.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.duiba.bean.DuiBaMall;

public interface DuiBaMallDao extends BaseDao<DuiBaMall> {
	
    //查询-单条
    public DuiBaMall findOne(int id);
    
    //查询-分页
    public List<DuiBaMall> findList(String name, PageInfo pageinfo);
    
    //查询(已经启用的)
    public List<DuiBaMall> findEnabledList();
}
