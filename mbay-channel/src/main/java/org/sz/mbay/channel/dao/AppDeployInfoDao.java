package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.AppDeployInfo;

/**
 * 
 * @ClassName: AppDeployInfoDao
 * @Description: 服务部署信息数据库持久化层，封装类
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * @date 2014年11月25日 下午12:51:55
 * 
 */
public interface AppDeployInfoDao extends BaseDao<AppDeployInfo> {
    public List<AppDeployInfo> findAllAppDeployInfosByName(String name);

}
