package org.sz.mbay.trafficred.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;
import org.sz.mbay.trafficred.qo.TrafficRedCampaignQO;

/**
 * 流量红包活动模板
 * 
 * @author Fenlon
 * 		
 */
public interface TrafficRedTmplService {
	
	/**
	 * 创建活动模板
	 * 
	 * @param campaignId
	 * @param template
	 * @return
	 */
	TrafficRedTemplate create(TrafficRedTemplate template);
	
	/**
	 * 查找模板
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedTemplate find(long id);
	
	/**
	 * 根据条件跟新模板
	 * 
	 * @param template
	 * @return
	 */
	TrafficRedTemplate updateByIdSelective(TrafficRedTemplate template);
	
	/**
	 * 分页查询摇一摇定制相关记录
	 * 
	 * @param qo
	 * @param pageInfo
	 * @return
	 */
	List<TrafficRedTemplate> findPageOfShakeCustomized(TrafficRedCampaignQO qo,
			PageInfo pageInfo);
			
	/**
	 * 摇一摇定制信息清除
	 * 
	 * @param id
	 * @return
	 */
	boolean clearShakeCustomize(Long id);
	
	/**
	 * 摇一摇定制信息新增
	 * 
	 * @param bean
	 * @return
	 */
	boolean createShakeCustomize(TrafficRedTemplate bean);
	
	/**
	 * 摇一摇定制信息是否已存在
	 * 
	 * @param cid
	 * @return
	 */
	boolean isExistShakeCustomize(Long cid);
	
}
