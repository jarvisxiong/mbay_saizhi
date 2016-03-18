package org.sz.mbay.channel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.SMSPackage;
import org.sz.mbay.channel.dao.SMSPackageDao;

@Repository
public class SMSPackageDaoImpl extends BaseDaoImpl<SMSPackage> implements SMSPackageDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SMSPackageDaoImpl.class);

    @Override
    public List<SMSPackage> findAllSMSPackage() {
        List<SMSPackage> sMSPackageList= null;
        try {
            sMSPackageList = this.template.selectList("findAllSMSPackage");
        } catch (Exception e) {
            LOGGER.error("SMSPackage", e.fillInStackTrace());
        }
        return sMSPackageList;
    }
}

