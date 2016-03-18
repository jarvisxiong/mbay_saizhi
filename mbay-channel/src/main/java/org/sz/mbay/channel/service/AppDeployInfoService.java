package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.channel.bean.AppDeployInfo;

/**
 * 
 * @ClassName: AppDeployInfoService
 * @Description: 服务部署业务逻辑层封装类
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * @date 2014年11月25日 下午12:25:42
 * 
 */
public interface AppDeployInfoService {
    /**
     * 删除服务部署信息，因为appDeployInfo中使用的是联合主键（name+ip+port）基本上已经占完了封装的信息，
     * 所以在此按着实体对象来删除
     * 
     * @param appDeployInfo
     *            封装的服务部署信息对象
     */
    public int deleteAppDeployInfo(AppDeployInfo appDeployInfo);

    /**
     * 添加服务部署信息
     * 
     * @param appDeployInfo
     *            封装的服务部署信息对象
     */
    public AppDeployInfo createAppDeployInfo(AppDeployInfo appDeployInfo);

    public List<AppDeployInfo> findAllAppDeployInfosByName(String name);
}
