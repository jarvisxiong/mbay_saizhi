package org.sz.mbay.channel.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.channel.bean.ChannelPartner;
import org.sz.mbay.channel.dao.ChannelPartnerDao;
import org.sz.mbay.channel.service.ChannelPartnerService;

@Service
public class ChannelPartnerServiceImpl implements ChannelPartnerService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ChannelPartnerServiceImpl.class);

    @Autowired
    ChannelPartnerDao dao;

    @Override
    public List<ChannelPartner> findList() {
        List<ChannelPartner> list = null;
        try{
            list = this.dao.findList();
        }catch(Exception e){
            LOGGER.error("ChannelPartnerService FindList Error",e.fillInStackTrace());
        }
        return list;
    }
}
