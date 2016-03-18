package org.sz.mbay.duiba.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;

public interface DuiBaRelationShipDao extends BaseDao<DuiBaRelationShip> {
	
    //查询-单条
    public DuiBaRelationShip findOne(int id);
    
    //查询-分页
    public List<DuiBaRelationShip> findList(String usernumber, PageInfo pageinfo);
}
