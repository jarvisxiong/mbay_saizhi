package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.ChannelDynamic;
import org.sz.mbay.channel.bean.DynamicItem;

public interface ChannelDynamicDao extends BaseDao<ChannelDynamic> {

    public List<DynamicItem> findCategory();
}
