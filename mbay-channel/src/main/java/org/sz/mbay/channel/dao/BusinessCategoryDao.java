package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.channel.bean.BusinessCategory;

public interface BusinessCategoryDao {

    List<BusinessCategory> findAllBusinessCategory();

}
