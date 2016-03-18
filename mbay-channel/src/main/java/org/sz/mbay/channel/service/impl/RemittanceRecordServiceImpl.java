package org.sz.mbay.channel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.RemittanceRecord;
import org.sz.mbay.channel.dao.RemittanceRecordDao;
import org.sz.mbay.channel.service.RemittanceRecordService;

@Service
public class RemittanceRecordServiceImpl implements RemittanceRecordService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RemittanceRecordServiceImpl.class);

    @Autowired
    RemittanceRecordDao dao;

    @Override
    public List<RemittanceRecord> findList(RemittanceRecord bean,PageInfo pageinfo) {
    	try {
            return this.dao.findList(bean, pageinfo);
        } catch (Exception e) {
            LOGGER.error("RemittanceRecordService findList Error",e.fillInStackTrace());
        }
        return new ArrayList<RemittanceRecord>();
    }

    @Override
    public void add(RemittanceRecord bean) {
        try {
            this.dao.createBean(bean);
        } catch (Exception e) {
            LOGGER.error("RemittanceRecordService add Error", e.fillInStackTrace());
        }
    }
    
    @Override
    public RemittanceRecord findRemittanceRecordById(int id) {
    	RemittanceRecord bean = null;
        try{
            bean = this.dao.findRemittanceRecordById(id);
        }catch(Exception e){
            LOGGER.error("RemittanceRecordService findRemittanceRecordById Error",e.fillInStackTrace());
        }
        return bean;
    }
}