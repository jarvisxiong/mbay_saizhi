package org.sz.mbay.channel.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.RemittanceRecord;
import org.sz.mbay.channel.dao.RemittanceRecordDao;

@Repository
public class RemittanceRecordDaoImpl extends BaseDaoImpl<RemittanceRecord> implements RemittanceRecordDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RemittanceRecordDaoImpl.class);
    
    @Override
    public RemittanceRecord findRemittanceRecordById(int id) {
    	RemittanceRecord bean = null;
        try{
        	bean = this.template.selectOne("findRemittanceRecordById",id);
        }catch(Exception e){
            LOGGER.error("RemittanceRecordDao findRemittanceRecordById Error",e.fillInStackTrace());
        }
        return bean;
    }
}