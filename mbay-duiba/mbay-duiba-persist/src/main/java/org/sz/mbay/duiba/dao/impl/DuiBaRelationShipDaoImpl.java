package org.sz.mbay.duiba.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;
import org.sz.mbay.duiba.dao.DuiBaRelationShipDao;

@Repository
public class DuiBaRelationShipDaoImpl extends BaseDaoImpl<DuiBaRelationShip> implements DuiBaRelationShipDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DuiBaRelationShipDaoImpl.class);

    @Override
    public DuiBaRelationShip findOne(int id) {
    	DuiBaRelationShip bean = null;
        try{
        	bean = this.template.selectOne("findDuiBaRelationShipById",id);
        }catch(Exception e){
            LOGGER.error("DuiBaRelationShipDaoImpl findOne Error",e.fillInStackTrace());
        }
        return bean;
    }
    
    @Override
	public List<DuiBaRelationShip> findList(String usernumber, PageInfo pageinfo){
    	List<DuiBaRelationShip> list= null;
        try {
            list = super.findList(usernumber, pageinfo, "DuiBaRelationShip");
        } catch (Exception e) {
            LOGGER.error("DuiBaRelationShipDaoImpl findList Error", e.fillInStackTrace());
        }
        return list;
	}
}