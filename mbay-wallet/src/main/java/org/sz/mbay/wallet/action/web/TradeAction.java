package org.sz.mbay.wallet.action.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ResponseFail;
import org.sz.mbay.base.wrap.ResponseSuccess;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.duiba.service.DuiBaMallService;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.remote.interfaces.wallet.RITradeRecordUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGift;
import org.sz.mbay.trafficred.service.TrafficRedCampaignService;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftService;
import org.sz.mbay.wallet.bean.WeChatShare;
import org.sz.mbay.wallet.context.WalletConfig;
import org.sz.mbay.wallet.context.WalletContext;
import org.sz.mbay.wallet.service.WeChatShareService;
import org.sz.mbay.weixin.WeChatJSUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("Web_TradeAction")
@RequestMapping("web/trade")
public class TradeAction {
	
	@Autowired
	private WeChatShareService shareService;
	@Autowired
	private TrafficOrderService trafficOrderService;
	@Autowired
	private DuiBaMallService duiBaMallService;
	@Autowired
	private TrafficRedCampaignService trafficRedCampaignService;
	@Autowired
	private TrafficRedExchangeRecordService trafficRedExchangeRecordService;
	@Autowired
	private TrafficRedMbayGiftService trafficRedMbayGiftService;
	@Autowired
	private PromotionCampaignService promotionCampaignService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TradeAction.class);
			
	// fastdfs文件管理
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	/**
	 * 交易记录界面
	 * 
	 * @param mobile
	 * @param model
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("record/list/ui")
	public String recordListUi(
			PageInfo pageInfo,
			Model model,
			HttpServletRequest request) {
		String mobile = WalletContext.getSessionUser().getMobile();
		JSONArray data = null;
		try {
			RIResponse resp = RITradeRecordUtil.getTradeRecordList(mobile, null,
					pageInfo.getPagenum(), pageInfo.getPagerow());
			data = resp.getData().getJSONArray("result");
			model.addAttribute("totalPage", resp.getData().getInt("totalPage"));
		} catch (Exception e) {
			LOGGER.error("get trade records error:{}", e.getMessage());
			data = new JSONArray();
			pageInfo.setTotalpage(1);
		}
		
		model.addAttribute("records", data);
		model.addAttribute("mobile", mobile);
		model.addAttribute("pageInfo", pageInfo);
		
		// 配置微信js
		String share_url = WalletConfig.MOBILE_DOMAIN
				+ request.getRequestURI();
		if (!"1".equals(pageInfo.getPagenum())) {
			share_url += "?pagenum=" + pageInfo.getPagenum();
		}
		wechatConfig(share_url, model);
		return "trade/record/list";
	}
	
	/**
	 * 获取详情
	 * 
	 * @param serialNumber
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("record/get")
	public Object recordGet(@RequestParam("serialNumber") String serialNumber,
			Model mode) {
		// 获取交易基本信息
		RIResponse resp = null;
		JSONObject data = new JSONObject();
		try {
			resp = RITradeRecordUtil
					.requestGetTradeRecordBySerialNum(serialNumber);
		} catch (Exception e) {
			LOGGER.error("get trade record error:{}", e.getMessage());
			return ResponseFail.create(e.getMessage());
		}
		
		JSONObject result = resp.getData().getJSONObject("result");
		String tradeType = result.getString("tradeType");
		String relatedNumber = result.getString("relatedNumber");
		String mobile = WalletContext.getSessionUser().getMobile();
		
		data.put("tradeType", tradeType);
		data.put("serialNumber", serialNumber);
		data.put("relatedNumber", relatedNumber);
		data.put("paymentType", result.getString("paymentType"));
		data.put("amount", result.getString("amount"));
		data.put("description", result.getString("description"));
		data.put("createTime", result.getString("createTime"));
		
		// 部分老记录没有记录相关单号
		if (StringUtils.isNotEmpty(relatedNumber)) {
			// 不同交易类型请求相应的接口
			if ("TRAFFIC_EXCHANGE".equals(tradeType)) {
				TrafficOrder to = trafficOrderService
						.findTrafficOrder(relatedNumber);
				data.put("ors", to.getOrs().getValue());
				data.put("status", to.getStatus().getValue());
			} else if ("DUIBA_MARKET".equals(tradeType)) {
				setDuibaMallInfo(Integer.parseInt(relatedNumber), mobile, data);
			} else if ("TRAFFIC_RED_MBAY".equals(tradeType)
					|| "TRAFFIC_RED_TRAFFIC".equals(tradeType)) {
				TrafficRedExchangeRecord et = trafficRedExchangeRecordService
						.findById(Long.parseLong(relatedNumber));
				TrafficRedCampaign cmp = trafficRedCampaignService
						.findCampaignByNumber(et.getCampaignNumber());
				data.put("name", cmp.getName());
				data.put("number", et.getCampaignNumber());
				data.put("status", cmp.getStatus().getValue());
				data.put("packageStatus", et.getPackageState().getValue());
				setDuibaMallInfo(cmp.getMall().getId(), mobile, data);
			} else if ("DUIBA_MARKET_ROLLBACK".equals(tradeType)) {
				RIResponse trResp = null;
				try {
					trResp = RITradeRecordUtil
							.requestGetTradeRecordBySerialNum(relatedNumber);
				} catch (Exception e) {
					LOGGER.error("lookup trade record error:{}",
							e.getMessage());
					return ResponseFail.create(e.getMessage());
				}
				String related = trResp.getData().getJSONObject("result")
						.getString("relatedNumber");
				setDuibaMallInfo(Integer.parseInt(related), mobile, data);
			} else if ("TRAFFIC_RED_MBAY_GIFT_GRAB".equals(tradeType)) {
				TrafficRedMbayGift gift = trafficRedMbayGiftService
						.findById(Long.parseLong(relatedNumber));
				data.put("sender", gift.getSender());
				data.put("giftState", gift.getMbayGiftState().getValue());
			} else if ("PROMOTION_MBAY".equals(tradeType)) {
				PromotionCampaign cmp = promotionCampaignService
						.findPromotionCampaign(relatedNumber);
				data.put("name", cmp.getEventname());
				data.put("number", cmp.getEventnumber());
				data.put("status", cmp.getState());
				setDuibaMallInfo(cmp.getMall().getId(), mobile, data);
			}
		}
		return ResponseSuccess.create(data);
	}
	
	/*
	 * 设置商城信息
	 */
	private void setDuibaMallInfo(int mallId, String mobile, JSONObject data) {
		if (mallId == 0) {
			mallId = Integer.parseInt(WalletConfig.MALLID);
		}
		DuiBaMall mall = duiBaMallService.findOne(mallId);
		data.put("mallName", mall.getName());
		String url = WalletConfig.DUIBA_URL;
		url = url.replace("MOBILE", DigestUtils.pbeEncrypt(mobile));
		url = url.replace("MALLID", String.valueOf(mallId));
		data.put("url", url);
	}
	
	/*
	 * 配置微信
	 */
	private void wechatConfig(String url, Model attributes) {
		List<WeChatShare> list = shareService.findAllWeChatShare();
		if (list != null && list.size() > 0) {
			WeChatShare bean = list.get(0);
			attributes.addAttribute("content", bean.getContent());
			attributes.addAttribute("shareTitle", bean.getTitle());
			attributes.addAttribute("shareLink", bean.getLink());
			FDFSFileInfo imageInfo = (FDFSFileInfo) fsClient.getFileInfo(bean
					.getImage());
			attributes.addAttribute("shareImage", imageInfo.getFullPath());
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Send to wechat URL:{}", url);
		}
		LOGGER.info("start request WeChatVerifyConfig");
		WeChatJSUtil.WeChatVerifyConfig parameter = WeChatJSUtil
				.getVerifyConfig(url);
		LOGGER.info("end request WeChatVerifyConfig");
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("WeChatVerifyConfig:{}", parameter.toString());
		}
		attributes.addAttribute("appId", parameter.getAppId());
		attributes.addAttribute("timestamp", parameter.getTimestamp());
		attributes.addAttribute("nonceStr", parameter.getNonceStr());
		attributes.addAttribute("signature", parameter.getSignature());
	}
	
}
