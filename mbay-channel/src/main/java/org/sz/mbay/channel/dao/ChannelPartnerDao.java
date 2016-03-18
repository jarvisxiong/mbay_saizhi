package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.ChannelPartner;

public interface ChannelPartnerDao extends BaseDao<ChannelPartner> {

    //查询
    List<ChannelPartner> findList();
}
