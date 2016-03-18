package org.sz.mbay.channel.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.channel.bean.SMSPackage;
import org.sz.mbay.channel.dao.SMSPackageDao;
import org.sz.mbay.channel.service.SMSPackageService;

@Service
public class SMSPackageServiceImpl implements SMSPackageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSPackageServiceImpl.class);

    @Autowired
    SMSPackageDao sMSPackageDao;

    @Override
    public List<SMSPackage> findAllSMSPackage() {
        List<SMSPackage> sMSPackageList = null;
        try{
            sMSPackageList = this.sMSPackageDao.findAllSMSPackage();
        }catch(Exception e){
            LOGGER.error("findAllSMSPackage Error",e.fillInStackTrace());
        }
        return sMSPackageList;
    }

	@Override
	public SMSPackage findSMSPackage(int packageid) {
		try {
		  return this.sMSPackageDao.getBean(packageid);
		} catch (Exception e) {
			LOGGER.error("findSMSPackage Error",e.fillInStackTrace());
		}
		return null;
	}

}
