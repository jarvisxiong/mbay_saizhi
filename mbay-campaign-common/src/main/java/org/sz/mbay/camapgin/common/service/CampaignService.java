package org.sz.mbay.camapgin.common.service;

import java.util.List;

/**
 * 在不同活动业务模板实现此Servcie
 * @author han.han
 *
 */
public interface CampaignService {

	default void campaignStart() {
	}

	default void campaignOver() {
	}

	/**
	 * 查询活动开始日期为今天的但未开始的活动
	 * @return 活动编号集合
	 */
	public List<String> findAllCampaignNumberStartToday();

	/**
	 * 查询活动截止日期是今天的但未结束的活动
	 * @return 活动编号集合
	 */
	public List<String> findAllCampaignNumberOverToday();

	/**
	 * 活动开始，修改活动状态为已开始等相关操作
	 * @param campaignNumber 活动编号
	 */
	public void startCampaign(String campaignNumber);

	/**
	 * 活动结束，修改活动状态为已结束，解锁美贝等相关操作
	 * @param campginNumber 活动编号
	 */
	public void overCampaign(String campaignNumber);

}
