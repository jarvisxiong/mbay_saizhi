package org.sz.mbay.channel.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.bean.Store;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.bean.StoreOperator;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.service.StoreActivityService;
import org.sz.mbay.channel.service.StoreOperatorService;
import org.sz.mbay.channel.service.StoreService;

/**
 * 
 * @author Fenlon
 * 
 */
@Controller
@RequestMapping("/store/")
public class StoreAction {
	
	public static String baseurl = "o2o_campaign";
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	StoreOperatorService storeOperatorService;
	@Autowired
	StoreActivityService campaignService;
	
	/**
	 * 门店管理视图
	 * 
	 * @return view
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		return baseurl + "/store/index";
	}
	
	/**
	 * 
	 * @return 初始化门店数量视图
	 */
	@RequestMapping(value = "initui", method = RequestMethod.GET)
	public String initStoreUI() {
		return baseurl + "/store/init";
	}
	
	/**
	 * 
	 * @return 门店详细信息视图
	 */
	/*
	 * @RequestMapping(value = "detail2", method = RequestMethod.GET)
	 * public String detail2(long id, Model model) {
	 * List<StoreOperator> storeOperators = null;
	 * String userNumber = ChannelContext.getSessionChannel().getUsernumber();
	 * Store store = this.storeService
	 * .findStoreByIdAndUser(id, userNumber);
	 * if (store == null) {
	 * return baseurl + "/store/detail";
	 * }
	 * // 查询门店对应的操作员信息
	 * storeOperators = this.storeOperatorService.findOperators(store.getId());
	 * // 查询该账户所有活动
	 * List<StoreCampaign> activities = this.campaignService
	 * .findActivitiesByUser(userNumber);
	 * // 挑出参加的活动
	 * StoreCampaign storeCampaign = null;
	 * for (StoreCampaignRecord record : store.getActivityRecords()) {
	 * if (record.isExited()) {
	 * continue;
	 * }
	 * storeCampaign = record.getStoreCampaign();
	 * if (activities.contains(storeCampaign)) {
	 * activities.remove(storeCampaign);
	 * }
	 * }
	 * model.addAttribute("store", store);
	 * model.addAttribute("storeOperators", storeOperators);
	 * model.addAttribute("notJoined", activities);
	 * return baseurl + "/store/detail";
	 * }
	 */
	
	/**
	 * 
	 * @return 门店详细信息视图
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(long id, Model model) {
		List<StoreOperator> storeOperators = null;
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		/*
		 * Store store = this.storeService
		 * .findStoreByIdAndUser(id, userNumber);
		 */
		
		if (userNumber == null) {
			throw new ServiceException(new ErrorInfo("NOT LOGIN", "请先登录!"));
		}
		Store store = this.storeService.findStoreById(id);
		
		if (store == null) {
			return baseurl + "/store/detail";
		}
		// 查询门店对应的操作员信息
		storeOperators = this.storeOperatorService.findOperators(store.getId());
		
		List<StoreCampaign> joinedCampaigns = this.storeService.findJoinedCampaignsByStatusAndTime(id, null);
		// 详细页面只显示5条数据，多余的数据去除（由于后面的xml中很乱，目前只能这样处理）
		if (joinedCampaigns.size() > 5) {
			int size = joinedCampaigns.size();
			for (int i = size - 1; i >= 5; i--) {
				joinedCampaigns.remove(i);
			}
		}
		
		// 查询为参与的当前处于活动中的活动
		List<StoreCampaign> notjoinedCampaigns = this.storeService.findNotJoinedCampaignsByStatusAndTime(id, userNumber, null);
		
		model.addAttribute("store", store);
		model.addAttribute("storeOperators", storeOperators);
		model.addAttribute("joined", joinedCampaigns);
		model.addAttribute("notJoined", notjoinedCampaigns);
		return baseurl + "/store/detail";
	}
	
	/**
	 * 初始化门店
	 * 
	 * @return
	 */
	@RequestMapping(value = "initStore", method = RequestMethod.POST)
	public String batchCreate(int storeNum, int operatorNum) {
		if (storeNum < 1 || operatorNum < 1) {
			return "error";
		}
		int result = this.storeService.batchCreate(storeNum, operatorNum,
				ChannelContext.getSessionChannel().getUserNumber());
		if (result <= 0) {
			return null;
		}
		return "redirect:" + "list.mbay";
	}
	
	/**
	 * 分页显示所有门店
	 * 
	 * @param pageinfo
	 *            分页实体
	 * @param model
	 *            保存页面数据
	 * @param storeId
	 *            索引时候使用
	 * @return
	 */
	@RequestMapping(value = "list")
	public String listStore(PageInfo pageinfo, Model model) {
		List<Store> stores = null;
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		stores = this.storeService.findAllStores(userNumber, pageinfo);
		if (stores.size() < 1) {
			return "redirect:" + "initui.mbay";
		}
		model.addAttribute("storelist", stores);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return baseurl + "/store/list";
	}
	
	/**
	 * 
	 * @param pageinfo
	 *            分页对象
	 * @param storeId
	 *            门店Id
	 * @param flag
	 *            查询参与和未参与标识（0为未参与，1为参与）
	 * @return
	 */
	@RequestMapping(value = "queryJoined")
	public String listJoinDetail(PageInfo pageinfo, long storeId, String flag, Model model) {
		if (flag == null || "not".equals(flag) || "".equals(flag)) {
			// 查询未参与
			String userNumber = ChannelContext.getSessionChannel().getUserNumber();
			if (userNumber == null) {
				// 重定向到登录页面
				return ChannelAction.REDIRECT_LOGIN;
			}
			if (storeId == 0) {
				throw new ServiceException(new ErrorInfo("STORE_ID NULL", "门店ID不能为空!"));
			}
			List<StoreCampaign> notjoinedCampaigns = this.storeService.findNotJoinedCampaignsByStatusAndTime(storeId, userNumber, pageinfo);
			model.addAttribute("campaigns", notjoinedCampaigns);
			model.addAttribute("flag", 0);
			model.addAttribute("storeId", storeId);
			
			return baseurl + "/store/store_campaign";
		}
		
		if ("joined".equals(flag)) {
			List<StoreCampaign> joinedCampaigns = this.storeService.findJoinedCampaignsByStatusAndTime(storeId, pageinfo);
			model.addAttribute("campaigns", joinedCampaigns);
			model.addAttribute("flag", 1);
			model.addAttribute("storeId", storeId);
			return baseurl + "/store/store_campaign";
		}
		throw new ServiceException(new ErrorInfo("UNKONW ERROR", "传递参数有问题!"));
	}
	
	/**
	 * 得到用户门店json串
	 * 
	 * @param pageinfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get(PageInfo pageinfo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		if (userNumber == null) {
			return null;
		}
		List<Store> stores = this.storeService.findAllStores(userNumber, pageinfo);
		map.put("stores", stores);
		map.put("page", pageinfo);
		return map;
	}
	
	/**
	 * 新增门店
	 * 
	 * @param storenum
	 *            门店数量
	 * @return
	 */
	@RequestMapping(value = "addStore", method = RequestMethod.POST)
	@ResponseBody
	public boolean addStore(int storeNum, int operatorNum) {
		if (storeNum < 1 || operatorNum < 1) {
			return false;
		}
		int result = this.storeService.batchCreate(storeNum, operatorNum,
				ChannelContext.getSessionChannel().getUserNumber());
		return result > 0;
	}
	
	/**
	 * 新增门店
	 * 
	 * @param storenum
	 *            门店数量
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult update(Store store) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		store.setUserNumber(userNumber);
		return this.storeService.update(store);
	}
	
	/**
	 * 删除门店
	 * 
	 * @param storeId
	 *            门店id
	 * @return
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable("id") long id) {
		return this.storeService.delete(id);
	}
	
	/**
	 * 删除门店
	 * 
	 * @param storeId
	 *            门店id
	 * @return
	 */
	@RequestMapping(value = "batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public ExecuteResult delete(@RequestParam("ids[]") long[] ids) {
		ExecuteResult result = new ExecuteResult(true, "SUCCESS", "批量删除成功!");
		if (ids == null) {
			result.setSuccess(false);
			result.setError_code("Failed");
			result.setMessage("批量删除失败!");
			return result;
		}
		int r = this.storeService.batchDelete(ids, ChannelContext
				.getSessionChannel().getUserNumber());
		if (r <= 0) {
			result.setSuccess(false);
			result.setError_code("Failed");
			result.setMessage("批量删除失败!");
			return result;
		}
		return result;
	}
	
	/**
	 * 查询门店活动
	 * 
	 * @param storeId
	 *            门店id
	 * @return
	 */
	@RequestMapping(value = "/{storeId}/listCampaign", method = RequestMethod.GET)
	public String listCampaign(@PathVariable("storeId") long storeId, Model model) {
		Store store = this.storeService.findStoreInfos(storeId);
		if (!store.isEnable()) {
			model.addAttribute("enable", store.isEnable());
			return baseurl + "/store/listCampaign";
		}
		// 获得store参与的有效活动
		List<StoreCampaign> campaigns = this.storeService.findValidActivity(storeId, store.getUserNumber());
		model.addAttribute("campaigns", campaigns);
		model.addAttribute("store", store);
		return baseurl + "/store/listCampaign";
	}
	
	/**
	 * 删除门店
	 * 
	 * @param storeId
	 *            门店id
	 * @return
	 */
	@RequestMapping(value = "exit", method = RequestMethod.POST)
	@ResponseBody
	public boolean exit(long id, long campaignId) {
		if (id == 0 || campaignId == 0) {
			return false;
		}
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		int result = this.storeService.exitActivity(id, campaignId, userNumber);
		return result > 0;
	}
	
	/**
	 * 参加活动
	 * 
	 * @param id
	 *            门店id
	 * @return
	 */
	@RequestMapping(value = "join", method = RequestMethod.POST)
	@ResponseBody
	public boolean join(Long id, Long campaignId) {
		if (id == null || campaignId == null) {
			return false;
		}
		
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		int result = this.storeService.joinActivity(id, campaignId, userNumber);
		return result > 0;
	}
	
	/**
	 * 加入活动(并检查是否已经加入过该活动)
	 * 
	 * @param id
	 *            门店id
	 * @return
	 */
	@RequestMapping(value = "join_check", method = RequestMethod.POST)
	@ResponseBody
	public boolean joinWithCheck(Long id, Long campaignId) {
		if (id == null || campaignId == null) {
			return false;
		}
		
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		boolean exist = this.storeService.chexkIsJoined(id, campaignId);
		if (exist) {
			return this.storeService.continueJoinActivity(id, campaignId, userNumber) > 0;
		}
		int result = this.storeService.joinActivity(id, campaignId, userNumber);
		return result > 0;
	}
	
	/**
	 * 继续参加活动
	 * 
	 * @param id
	 *            门店id
	 * @return
	 */
	@RequestMapping(value = "continueJoin", method = RequestMethod.POST)
	@ResponseBody
	public boolean continueJoin(long id, long campaignId) {
		if (id == 0 || campaignId == 0) {
			return false;
		}
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		int result = this.storeService.continueJoinActivity(id, campaignId,
				userNumber);
		return result > 0;
	}
	
	/**
	 * 门店信息excel视图
	 * 
	 * @param mm
	 * @return 门店excel视图Bean
	 */
	@RequestMapping(value = "store.xls")
	public String showStoreInExcel(ModelMap mm) {
		String userNumber = ChannelContext.getSessionChannel().getUserNumber();
		List<Store> stores = storeService.findAll(userNumber);
		mm.addAttribute("storeList", stores);
		return "storeListExcel";
	}
	
}
