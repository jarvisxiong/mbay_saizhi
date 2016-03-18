package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.SMSPackage;

public interface SMSPackageDao extends BaseDao<SMSPackage> {

    /**
     * 查询短信套餐列表
     * @return
     */
    List<SMSPackage> findAllSMSPackage();

}
