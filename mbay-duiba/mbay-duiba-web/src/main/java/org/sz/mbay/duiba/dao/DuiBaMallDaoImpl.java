package org.sz.mbay.duiba.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.duiba.bean.DuiBaMall;

@Repository
public class DuiBaMallDaoImpl extends BaseDaoImpl<DuiBaMall> implements DuiBaMallDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DuiBaMallDaoImpl.class);

    public DuiBaMall findOne(int id) {
    	DuiBaMall bean = null;
        try{
        	bean = this.template.selectOne("findDuiBaMallById",id);
        }catch(Exception e){
            LOGGER.error("DuiBaMallDaoImpl findOne Error",e.fillInStackTrace());
        }
        return bean;
    }
}