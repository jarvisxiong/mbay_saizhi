package org.sz.mbay.trafficred.service;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGift;
import org.sz.mbay.trafficred.result.Result;

/**
 * MB送人服务
 * 
 * @author jerry
 */
public interface TrafficRedMbayGiftService extends BaseService {
	
	/**
	 * 创建实体
	 * 
	 * @param gift
	 * @return
	 */
	boolean create(TrafficRedMbayGift gift);
	
	/**
	 * 根据相关单号查找一条记录
	 * 
	 * @param decNum
	 * @return
	 */
	TrafficRedMbayGift findBySerialNumber(String decNum);
	
	/**
	 * 根据相关单号检测记录是否存在
	 * 
	 * @param decNum
	 * @return
	 */
	boolean checkExistBySerialNumber(String decNum);
	
	/**
	 * 更新
	 * 
	 * @param gift
	 * @return
	 */
	boolean updateByIdSelective(TrafficRedMbayGift gift);
	
	/**
	 * 检测号码是否可领取
	 * 
	 * @param mobile
	 * @return
	 */
	Result checkParticipable(String mobile);
	
	/**
	 * 领取
	 * 
	 * @param id
	 * @param serialNumber
	 * @param mobile
	 * @return
	 */
	Result grab(Long id, String serialNumber, String mobile);
	
	/**
	 * 查找
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedMbayGift findById(long id);
	
}
