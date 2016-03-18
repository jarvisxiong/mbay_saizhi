package org.sz.mbay.channel.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.constant.CampaignConstant;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.CampaignRedeemCode;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.service.CampaignRedeemCodeService;
import org.sz.mbay.channel.service.StoreActivityService;
import org.sz.mbay.channel.service.StoreService;
import org.sz.mbay.channel.wrap.O2OCampaignRedeenDetail;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;

/**
 * O2O活动控制类
 * 
 * @author Fenlon
 * 
 */
@Controller
@RequestMapping(value = "/campaign/")
public class StoreCampaignAction {
	
	// 对于前端展示的基本页面
	private static final String BASEURL = "o2o_campaign/campaign/";
	// 用于区分是否拦截
	private static final String JOIN = "join";
	
	@Autowired
	StoreActivityService storeActivityService;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	CampaignRedeemCodeService campaignRedeemCodeService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "createUI", method = RequestMethod.GET)
	public String create() {
		return BASEURL + "create";
	}
	
	@RequestMapping(value = "createUI3", method = RequestMethod.GET)
	public String create3() {
		return BASEURL + "create3";
	}
	
	/**
	 * 分页显示用户的活动
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(PageInfo info, Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		List<StoreCampaign> campaigns = this.storeActivityService
				.findActivitiesByUser(userNumber, info);
		model.addAttribute("campaigns", campaigns);
		model.addAttribute(Globals.PAGEINFO_KEY, info);
		return BASEURL + "list";
	}
	
	/**
	 * 计算需要锁定的美贝值
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "countLockMbay", method = RequestMethod.POST)
	@ResponseBody
	public double countLockMbay(double price, int quantity) {
		if (price == 0.0 || quantity == 0) {
			this.logger.error("countLockMbay", "前台传过来的值有问题！");
			return 0.0;
		}
		double redeemDeduct = (price / CampaignConstant.MBAY_PRICE)
				* CampaignConstant.O2O_CAMPAIGN_REDEEM_PERCENT;
		double lock = (redeemDeduct + 1) * quantity;
		return lock;
	}
	
	/**
	 * 查询活动全部的兑换详细
	 * 
	 * @param campaignId
	 *            活动ID
	 * @return
	 */
	@RequestMapping(value = "redeem_detail", method = RequestMethod.GET)
	public String redeemDetail(long campaignId, PageInfo pageInfo, Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		StoreCampaign campaign = this.storeActivityService
				.findCampaignById(campaignId);
		List<O2OCampaignRedeenDetail> redeenDetails = this.campaignRedeemCodeService
				.queryRedeemDetail(campaignId, userNumber, pageInfo);
		model.addAttribute("redeemDetail", redeenDetails);
		model.addAttribute(Globals.PAGEINFO_KEY, pageInfo);
		model.addAttribute("campaign", campaign);
		return BASEURL + "redeem_detail";
	}
	
	/**
	 * 根据条件查询的活动兑换信息
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param pageInfo
	 *            分页信息
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param cellPhone
	 *            手机号
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "redeem_detail_with_con",
			method = RequestMethod.POST)
	public String redeemDetailByCondition(
			long campaignId,
			PageInfo pageInfo,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(required = false) String cellPhone, Model model) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		DateTime start = null;
		DateTime end = null;
		if ((startTime == "" || startTime == null)) {
			throw new ServiceException(new ErrorInfo("DATETIME FORMAT ERROR",
					"输入时间格式错误"));
		}
		if ((endTime == "" || endTime == null)) {
			throw new ServiceException(new ErrorInfo("DATETIME FORMAT ERROR",
					"输入时间格式错误"));
		}
		
		if ("".equals(cellPhone)) {
			cellPhone = null;
		}
		
		startTime = startTime + " 00:00:00";
		endTime = endTime + " 23:59:59";
		start = MbayDateFormat.stringToTime(startTime);
		end = MbayDateFormat.stringToTime(endTime);
		
		StoreCampaign campaign = this.storeActivityService
				.findCampaignById(campaignId);
		List<O2OCampaignRedeenDetail> redeenDetails = this.campaignRedeemCodeService
				.queryRedeemDetailByCondition(campaignId, userNumber, start,
						end, cellPhone, pageInfo);
		model.addAttribute("redeemDetail", redeenDetails);
		model.addAttribute(Globals.PAGEINFO_KEY, pageInfo);
		model.addAttribute("campaign", campaign);
		return BASEURL + "redeem_detail";
	}
	
	/**
	 * 查询活动详细信息
	 * 
	 * @param campaignId
	 *            活动ID
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(long campaignId, Model model) {
		StoreCampaign campaign = this.storeActivityService
				.findCampaignById(campaignId);
		model.addAttribute("campaign", campaign);
		return BASEURL + "campaign_detail";
	}
	
	/**
	 * 分页显示用户的活动
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get(PageInfo info, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		if (userNumber == null) {
			return null;
		}
		List<Store> stores = this.storeService.findAllStores(userNumber, info);
		map.put("stores", stores);
		map.put("page", info);
		return map;
	}
	
	/**
	 * 
	 * 门店活动详细
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = JOIN + "/{storeId}/{campaignId}/detail",
			method = RequestMethod.GET)
	public String detail(@PathVariable long storeId,
			@PathVariable long campaignId, Model model) {
		StoreCampaign campaign = this.storeActivityService
				.findCampaignById(campaignId);
		model.addAttribute("campaign", campaign);
		model.addAttribute("storeId", storeId);
		return BASEURL + "detail";
	}
	
	/**
	 * 根据用户手机号，发放活动兑换码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = JOIN + "/join", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult join(long storeId, long campaignId, String cellPhone,
			HttpServletResponse response, Model model) {
		StoreCampaign campaign = this.storeActivityService
				.findCampaignById(campaignId);
		// 根据活动信息，来判断是否可以为C产生兑换码,还包括手机号的检测（是否重复领取）
		ExecuteResult result = this.campaignRedeemCodeService.judgeValidate(
				campaign, cellPhone);
		
		if (!result.isSuccess()) {
			return result;
		}
		// 生成兑换码
		CampaignRedeemCode redeemCode = this.campaignRedeemCodeService
				.createRedeemCode(cellPhone, storeId, campaign);
		String code = redeemCode.getRedeemCode();
		code = DigestUtils.pbeEncrypt(code);
		String storeid = DigestUtils.pbeEncrypt(storeId + "");
		result.setError_code(storeid);
		result.setMessage(code);
		return result;
	}
	
	/**
	 * 客户参与活动成功后跳转到成功页面
	 * 
	 * @param code
	 *            兑换码
	 * @param storeId
	 *            门店ID
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(JOIN + "/success")
	public String joinSuccess(
			@RequestParam(value = "code", required = false) String code,
			@RequestParam String storeId, Model model) throws IOException {
		if (code == null || "".equals(code)) {
			throw new IOException();
		}
		
		if (storeId == null || "".equals(storeId)) {
			throw new IOException();
		}
		model.addAttribute("storeId", DigestUtils.pbeDecrypt(storeId));
		model.addAttribute("code", DigestUtils.pbeDecrypt(code));
		return BASEURL + "success";
	}
	
	/**
	 * 根据Id删除用户的活动
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") long id) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		this.storeActivityService.delete(id, userNumber);
		return "redirect:/" + "campaign/list.mbay";
	}
	
	/**
	 * 根据Id取消活动
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/cancel", method = RequestMethod.GET)
	public String cancel(@PathVariable("id") long id) {
		this.storeActivityService.cancelCampaign(id);
		return "redirect:/" + "campaign/list.mbay";
	}
	
	/**
	 * 跟新活动
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult update(StoreCampaign campaign) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		campaign.setUserNumber(userNumber);
		return this.storeActivityService.update(campaign);
		
	}
	
	/**
	 * 检查活动是否过期
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "checkIsExpire", method = RequestMethod.GET)
	@ResponseBody
	public ExecuteResult checkIsExpire() {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		return this.storeActivityService.checkIsExpire(userNumber);
	}
	
	/**
	 * 查询没有没有参与过该活动的门店
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chooseStore")
	public String chooseStore(long campaignId, PageInfo info, Model model) {
		// 查询没有参加该活动的门店
		StoreCampaign campaign = this.storeActivityService
				.findCampaignById(campaignId);
		if (campaign == null) {
			throw new ServiceException(new ErrorInfo("CAMPAIGN NOT EXIST",
					"活动不存在"));
		}
		List<Store> stores = this.storeService.findNotJoinedStores(campaignId,
				info);
		model.addAttribute("storeslist", stores);
		model.addAttribute(Globals.PAGEINFO_KEY, info);
		model.addAttribute("campaignId", campaignId);
		return BASEURL + "chooseStore";
	}
	
	/**
	 * 
	 * 活动创建后选择要参与的门店
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectStore", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult selectStore(@RequestParam("ids[]") long[] ids,
			long campaignId) {
		return this.storeActivityService.selectStore(campaignId, ids);
	}
	
	/**
	 * 创建门店活动
	 * 
	 * @param campaign
	 *            活动实体
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult create(StoreCampaign campaign,
			@RequestParam("start_Time") String startTime,
			@RequestParam("end_Time") String endTime) {
		
		ExecuteResult result = new ExecuteResult(false, "FAILED", "");
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		if (userNumber == null) {
			this.logger.info("创建活动时，商户不能为空哦！");
			result.setMessage("创建活动时，商户不能为空哦！");
			return result;
		}
		startTime = startTime + " 00:00:00";
		endTime = endTime + " 23:59:59";
		DateTime start = MbayDateFormat.stringToTime(startTime);
		DateTime end = MbayDateFormat.stringToTime(endTime);
		campaign.setStartTime(start);
		campaign.setEndTime(end);
		campaign.setUserNumber(userNumber);
		// 活动状态设置
		DateTime now = DateTime.now();
		if (now.isAfter(end)) {
			// 如果结束时间在创建之前,设置为结束
			campaign.setStatus(CampaignStatus.OVER);
		} else {
			if (now.isBefore(start)) {
				// 如果开始时间在创建时间之后，设置为为开始
				campaign.setStatus(CampaignStatus.NOT_STARTED);
			} else {
				// 如果开始时间在创建时间之前，设置为活动中
				campaign.setStatus(CampaignStatus.IN_ACTIVE);
			}
		}
		StoreCampaign storeActivity = this.storeActivityService.createActivity(
				campaign, userNumber);
		if (storeActivity == null) {
			result.setMessage("Service创建活动失败！");
			return result;
		}
		result.setError_code("SUCCESS");
		result.setSuccess(true);
		result.setMessage(storeActivity.getId() + "");
		return result;
	}
	
	/**
	 * 活动列表excel视图
	 * 
	 * @param mm
	 * @return 活动列表excel视图Bean
	 */
	@RequestMapping(value = "campaign.xls")
	public String showCampaignInExcel(ModelMap mm) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		List<StoreCampaign> campaigns = storeActivityService
				.finaAllByUser(userNumber);
		mm.addAttribute("campaignList", campaigns);
		return "campaignListExcel";
	}
}
