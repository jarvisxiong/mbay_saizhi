package org.sz.mbay.channel.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.ChannelDynamic;
import org.sz.mbay.channel.bean.DynamicItem;
import org.sz.mbay.channel.dao.ChannelDynamicDao;

@Repository
public class ChannelDynamicDaoImpl extends BaseDaoImpl<ChannelDynamic> implements ChannelDynamicDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ChannelDynamicDaoImpl.class);

    @Override
	public List<DynamicItem> findCategory(){
		try {
            return template.selectList("selectCategory");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ArrayList<DynamicItem>();
	}
}

