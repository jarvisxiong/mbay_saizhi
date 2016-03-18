package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.channel.bean.SMSPackage;

public interface SMSPackageService {

    /**
     * 查询短信套餐列表
     * @return
     */
    public List<SMSPackage> findAllSMSPackage();
    
    /**
     * 根据短信套餐编号查询套餐信息
     * @param packageid
     * @return
     */
    public SMSPackage findSMSPackage(int packageid);

}
