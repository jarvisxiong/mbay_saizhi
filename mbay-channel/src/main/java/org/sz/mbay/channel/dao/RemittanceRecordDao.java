package org.sz.mbay.channel.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.RemittanceRecord;

public interface RemittanceRecordDao extends BaseDao<RemittanceRecord> {
		
    //查询-单条
    public RemittanceRecord findRemittanceRecordById(int id);
    
}