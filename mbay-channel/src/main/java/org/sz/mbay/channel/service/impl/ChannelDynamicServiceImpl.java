package org.sz.mbay.channel.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.channel.bean.DynamicItem;
import org.sz.mbay.channel.dao.ChannelDynamicDao;
import org.sz.mbay.channel.service.ChannelDynamicService;

@Service
public class ChannelDynamicServiceImpl extends BaseServiceImpl implements ChannelDynamicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelDynamicServiceImpl.class);

    @Autowired
    ChannelDynamicDao dao;

    @Override
    public List<DynamicItem> findCategory() {
    	try {
            return this.dao.findCategory();
        } catch (Exception e) {
            LOGGER.error("findCategory",e.fillInStackTrace());
        }
        return null;
    }
}
