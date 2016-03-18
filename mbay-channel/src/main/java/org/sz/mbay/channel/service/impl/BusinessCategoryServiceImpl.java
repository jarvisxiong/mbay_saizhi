package org.sz.mbay.channel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.bean.BusinessCategory;
import org.sz.mbay.channel.dao.BusinessCategoryDao;
import org.sz.mbay.channel.service.BusinessCategoryService;

@Service
public class BusinessCategoryServiceImpl extends BaseServiceImpl implements BusinessCategoryService {

    @Autowired
    BusinessCategoryDao businessCategoryDao;

    @Override
    public List<BusinessCategory> findAllBusinessCategory() {
        return businessCategoryDao.findAllBusinessCategory();
    }
}
