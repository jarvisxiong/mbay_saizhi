package org.sz.mbay.channel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.ChannelPartner;
import org.sz.mbay.channel.dao.ChannelPartnerDao;

@Repository
public class ChannelPartnerDaoImpl extends BaseDaoImpl<ChannelPartner> implements ChannelPartnerDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ChannelPartnerDaoImpl.class);

    @Override
    public List<ChannelPartner> findList() {
        List<ChannelPartner> list= null;
        try {
            list = this.template.selectList("findChannelPartnerList");
        } catch (Exception e) {
            LOGGER.error("ChannelPartnerDao findChannelPartnerList Error", e.fillInStackTrace());
        }
        return list;
    }
}

