package org.sz.mbay.channel.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sz.mbay.apptemptation.service.AppTemptationService;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.service.StoreActivityService;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.wechat.service.WeChatCampaignService;

/**
 * @Description: 活动统计
 * @author frank.zong
 * @date 2015-1-7 上午10:57:51
 * 		
 */
@Controller
@RequestMapping("eventStatistics")
public class CampaignStatisticsAction extends BaseAction {
	
	// 统计页面
	public static final String EVENT_STATISTICS = "campaignStatistics/statistics";
	
	@Autowired
	WeChatCampaignService weChatService;
	
	@Autowired
	PromotionCampaignService promotionService;
	
	@Autowired
	AppTemptationService appTemptationService;
	
	@Autowired
	StoreActivityService storeActivityService;
	
	@Autowired
	TrafficRedCampaignService trafficRedCampaignService;
	
	/**
	 * 跳转至统计页面
	 * 
	 * @return
	 */
	@RequestMapping("event_statistics")
	public String to_event_statistics(Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		model.addAttribute("weChatStatistics",
				weChatService.statisticWeChatCampaign(userNumber));
		model.addAttribute("promotionStatistics",
				promotionService.statisticCampaign(userNumber));
		model.addAttribute("appTemptationStatistics",
				appTemptationService.statisticCampaign(userNumber));
		model.addAttribute("trafficRedStatistics",
				trafficRedCampaignService.statisticCampaign(userNumber));
		// model.addAttribute("storeCampaignStatistics",
		// storeActivityService.statisticCampaign(usernumber));
		return EVENT_STATISTICS;
	}
}
