package org.sz.mbay.channel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.BusinessCategory;
import org.sz.mbay.channel.dao.BusinessCategoryDao;

@Repository
public class BusinessCategoryDaoImpl extends BaseDaoImpl<BusinessCategory> implements BusinessCategoryDao {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(BusinessCategoryDaoImpl.class);

    @Override
    public List<BusinessCategory> findAllBusinessCategory() {
        return super.template.selectList("findAllBusinessCategory");
    }
}
