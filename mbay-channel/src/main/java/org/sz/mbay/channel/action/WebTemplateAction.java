package org.sz.mbay.channel.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.sz.mbay.base.context.WebContext;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvertise;
import org.sz.mbay.wechat.bean.WeChatCampaignTemplate;
import org.sz.mbay.wechat.config.WeChatResourceConfig;
import org.sz.mbay.wechat.service.WeChatCampaignService;
import org.sz.mbay.weixin.WeChatJSUtil;

/**
 * @Description: 根据活动模板连接返回对应活动模板页面
 * @author han.han
 * @date
 * 
 */
@Controller
@RequestMapping("template")
public class WebTemplateAction extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebTemplateAction.class);
	// 微信伴侣兑换页面
	public static String WECHATWEB = "wechat/template/wechat_1";
	// 兑换失败页面
	public static final String TRAFFIC_FAIL = "wechat/trafficFail";
	// 兑换页面防盗链session下attribute名称
	private static final String WECHATKEY = "wechatKey";

	@Autowired
	WeChatCampaignService eventservice;
	@Autowired
	PromotionCampaignService redeemPromotionService;

	/**
	 * fastdfs文件管理
	 */
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);

	/**
	 * 设置session
	 * 
	 * @param modelid
	 * @param ticketId
	 * @throws IOException
	 */
	@RequestMapping(value = "/{modelid}/get", method = RequestMethod.GET)
	public String session(@PathVariable String modelid,
			@RequestParam(value = "tk", required = false) String ticketId,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(WECHATKEY, String.valueOf(Math.random()));// 设置随机数，防止用户复制领取页面直接打开，因充定向无法通过cookie获取
		if (StringUtils.isEmpty(ticketId)) {
			session.setAttribute("TEMPLATE_REQUEST_URL",
					request.getRequestURL());
		}
		// 不通过Spring
		// MVC做重定向，自己直接调用，以防止重定向后自动判断浏览器是否支持cookie，从而加上jsessionid这个非必要参数
		try {
			String url = request.getContextPath() + "/template/" + modelid
					+ "/redeem_immediately.mbay";
			if (!StringUtils.isEmpty(ticketId)) {
				url += "?tk=" + ticketId;
			}
			response.sendRedirect(url);
			return null;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return TRAFFIC_FAIL;
		}

	}

	/**
	 * 立即兑换(通过是否设置key,用来判断是否盗链)
	 * 
	 * @param modelid
	 * @param ticketId
	 * @return 如果正确则返回兑换页面,错误返回错误页面
	 * @throws IOException
	 */
	@RequestMapping(value = "/{modelid}/redeem_immediately", method = RequestMethod.GET)
	public String redeemImmediately(@PathVariable String modelid,
			@RequestParam(value = "tk", required = false) String ticketId,
			HttpServletRequest request, Model model) {
		LOGGER.info("进入redeemImmediately 方法");
		String key = (String) ChannelContext.getSessionAttribute(WECHATKEY);
		if (key == null) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("未从session中获取到" + WECHATKEY + "跳往失败页面");
			}
			return TRAFFIC_FAIL;
		}
		// 清除session中attribute
		WebContext.getHttpSession().removeAttribute(WECHATKEY);
		// 获取到活动模式id
		String mid = DigestUtils.pbeDecrypt(modelid);
		if (mid == null || mid.length() == 0) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.warn("解析modelid：" + modelid + "失败！跳往失败页面！");
			}
			return TRAFFIC_FAIL;
		}
		model.addAttribute("modelid", modelid);
		WeChatCampaignTemplate eventmodel = eventservice
				.findCampaignTemplate(Integer.valueOf(mid));
		// 是否可以直接领取,默认是不可以
		boolean direct_enable = false;
		if (eventmodel != null) {
			model.addAttribute("eventnumber",
					DigestUtils.pbeEncrypt(eventmodel.getEventnumber()));
			direct_enable = eventservice.findCampaignDirectEnable(eventmodel
					.getEventnumber());
			model.addAttribute("shareTimes", eventmodel.getShareTimes());
			if (eventmodel.getShareTimes() > 0) {
				model.addAttribute("content", eventmodel.getContent());
				model.addAttribute("shareTitle", eventmodel.getShareTitle());
				model.addAttribute("shareLink", eventmodel.getShareLink());
				String shareImage = "";
				if (eventmodel.getShareImage() != null) {// ==null表示未设置分享图片
					FDFSFileInfo share_fileinfo = (FDFSFileInfo) fsClient
							.getFileInfo(eventmodel.getShareImage());
					shareImage = share_fileinfo.getFullPath();
				}
				model.addAttribute("sharePhoto", shareImage);
			}
			FDFSFileInfo back_fileinfo = (FDFSFileInfo) fsClient
					.getFileInfo(eventmodel.getBackphoto());
			model.addAttribute("backPhoto", back_fileinfo.getFullPath());
			String successPhoto = "";
			if (eventmodel.getSuccessPhoto() != null) {
				FDFSFileInfo success_fileinfo = (FDFSFileInfo) fsClient
						.getFileInfo(eventmodel.getSuccessPhoto());
				successPhoto = success_fileinfo.getFullPath();
			}
			model.addAttribute("successPhoto", successPhoto);
			String advertisePhoto = "";
			WeChatCampaignAdvertise adver = getEventAdvertise();
			if (adver != null) {
				FDFSFileInfo advertise_fileinfo = (FDFSFileInfo) fsClient
						.getFileInfo(adver.getPicture());
				advertisePhoto = advertise_fileinfo.getFullPath();
				LOGGER.info("获取分享提示广告图片：url{},fullURL:",adver.getPicture(),advertisePhoto);
			} else {
				LOGGER.warn("未找到活动分享提示广告信息");
			}
			model.addAttribute("advertisePhoto", advertisePhoto);
		}
		model.addAttribute("directEnable", direct_enable);
		// 分享劵包id
		if (!StringUtils.isEmpty(ticketId)) {
			model.addAttribute("tk", ticketId);
		}

		// 配置微信js
		String uri = request.getRequestURI();
		String url = WeChatResourceConfig.getWeChatDomain() + uri;
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
		model.addAttribute("appId", parameter.getAppId());
		model.addAttribute("timestamp", parameter.getTimestamp());
		model.addAttribute("nonceStr", parameter.getNonceStr());
		model.addAttribute("signature", parameter.getSignature());
		// 配置分享图片默认前缀地址
		model.addAttribute("appDomain", WeChatResourceConfig.getWeChatDomain());
		return WECHATWEB;
	}

	private WeChatCampaignAdvertise getEventAdvertise() {
		// 活动广告
		List<WeChatCampaignAdvertise> campaignAdvertises = eventservice.findWeChatCampaignAdvertiseList();
		if (campaignAdvertises != null && campaignAdvertises.size() > 0) {
			return campaignAdvertises.get(0);
		}
		return null;
	}
}
