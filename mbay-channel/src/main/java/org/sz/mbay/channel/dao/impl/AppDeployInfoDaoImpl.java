package org.sz.mbay.channel.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.AppDeployInfo;
import org.sz.mbay.channel.dao.AppDeployInfoDao;

@Repository
public class AppDeployInfoDaoImpl extends BaseDaoImpl<AppDeployInfo>
	implements AppDeployInfoDao {

    @Override
    public List<AppDeployInfo> findAllAppDeployInfosByName(String name) {
	return this.template.selectList("findAllAppDeployInfosByName", name);
    }
}
