package org.sz.mbay.channel.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.base.constant.SerialSeed;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.UtilService;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.Error;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.duiba.bean.DuiBaRelationShip;
import org.sz.mbay.duiba.service.DuiBaRelationShipService;
import org.sz.mbay.email.MbayMail;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.fs.fastdfs.FDFSFileInfo;
import org.sz.mbay.mall.bean.MallCouponTicket;
import org.sz.mbay.mall.bean.MallExchangeItem;
import org.sz.mbay.mall.bean.MallExtendLimit;
import org.sz.mbay.mall.bean.MallPicture;
import org.sz.mbay.mall.enums.MallAudit;
import org.sz.mbay.mall.enums.MallItemType;
import org.sz.mbay.mall.enums.MallPictureType;
import org.sz.mbay.mall.enums.MallStatus;
import org.sz.mbay.mall.qo.MallExchangeItemQO;
import org.sz.mbay.mall.service.MallCouponTicketService;
import org.sz.mbay.mall.service.MallExchangeItemService;
import org.sz.mbay.mall.service.MallExtendLimitService;
import org.sz.mbay.mall.service.MallPictureService;

import net.sf.json.JSONObject;

/**
 * 微信分享设置
 * 
 * @author frank.zong
 */
@Controller
@RequestMapping("mall")
public class MallAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallAction.class);
	
	@Autowired
	MallExchangeItemService itemService;
	@Autowired
	UtilService utilService;
	@Autowired
	MallPictureService pictureService;
	@Autowired
	MallExtendLimitService limitService;
	@Autowired
	DuiBaRelationShipService relationService;
	@Autowired
	MallCouponTicketService ticketService;
	
	/** 兑换项列表页面 */
	public static final String EXCHANGE_ITEM_LIST = "mall/exchange_item/exchange_item_list";
	/** 重定向到兑换项列表页面 */
	public static final String REDIRECT_EXCHANGE_ITEM_LIST = "redirect:/mall/exchangeItem/list.mbay";
	/** 重定向到兑换项新增页面 */
	public static final String REDIRECT_EXCHANGE_ITEM_ADD = "redirect:/mall/exchangeItem/toExchangeItemAdd.mbay";
	/** 重定向到兑换项更新页面 */
	public static final String REDIRECT_EXCHANGE_ITEM_UPDATE = "redirect:/mall/exchangeItem/toExchangeItemUpdate.mbay";
	/** 重定向到券码新增页面 */
	public static final String REDIRECT_COUPON_TICKET_ADD = "redirect:/mall/exchangeItem/toCouponTicketAdd.mbay";
	/** 兑换项新增页面 */
	public static final String EXCHANGE_ITEM_ADD = "mall/exchange_item/add_exchange_item";
	/** 兑换项更新页面 */
	public static final String EXCHANGE_ITEM_UPDATE = "mall/exchange_item/update_exchange_item";
	/** 优惠券券码新增页面 */
	public static final String COUPON_TICKET_ADD = "mall/exchange_item/add_coupon_ticket";
	/** 优惠券券码列表页面 */
	public static final String COUPON_TICKET_LIST = "mall/exchange_item/coupon_ticket_list";
	/** 手机预览页面 */
	public static final String EXCHANGE_ITEM_PREVIEW = "mall/exchange_item/preview_exchange_item";
	
	/** fastdfs文件管理 */
	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);
	
	/**
	 * 兑换项列表页面
	 * 
	 * @param pageinfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/exchangeItem/list")
	public String list(MallExchangeItemQO qo, PageInfo pageinfo, Model model) {
		qo.setUserNumber(ChannelContext.getSessionChannel().getUserNumber());
		List<MallExchangeItem> list = itemService.findList(qo, pageinfo);
		model.addAttribute("list", list);
		model.addAttribute("pageinfo", pageinfo);
		model.addAttribute("qo", qo);
		model.addAttribute("mobileDomain", ResourceConfig.getWebDomain());
		return EXCHANGE_ITEM_LIST;
	}
	
	/**
	 * 兑换项新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/exchangeItem/toExchangeItemAdd")
	@Token(save = true)
	public String toExchangeItemAdd(MallItemType type, Model model) {
		model.addAttribute("type", type);
		// 获取商城
		List<DuiBaRelationShip> relationList = relationService.findList(ChannelContext.getSessionChannel().getUserNumber(), null);
		model.addAttribute("relationList", relationList);
		return EXCHANGE_ITEM_ADD;
	}
	
	/**
	 * 兑换项更新页面
	 * 
	 * @param model
	 * @param itemNumber
	 * @return
	 */
	@RequestMapping("/exchangeItem/toExchangeItemUpdate")
	@Token(save = true)
	public String toExchangeItemUpdate(Model model, String itemNumber) {
		// 兑换项
		MallExchangeItem bean = itemService.findOne(itemNumber);
		model.addAttribute("bean", bean);
		// 详细图
		List<MallPicture> pictures = pictureService.findDetails(itemNumber);
		List<MallPicture> details = new ArrayList<MallPicture>();
		for (MallPicture picture : pictures) {
			String image = picture.getPicture();
			picture.setPicture(getImageFullPath(image));
			details.add(picture);
		}
		model.addAttribute("details", details);
		// 缩略图
		MallPicture thumbnail = pictureService.findOne(itemNumber, MallPictureType.THUMBNAIL);
		if (thumbnail != null) {
			thumbnail.setPicture(getImageFullPath(thumbnail.getPicture()));
			model.addAttribute("thumbnail", thumbnail);
		}
		// 图标
		MallPicture icon = pictureService.findOne(itemNumber, MallPictureType.ICON);
		if (icon != null) {
			icon.setPicture(getImageFullPath(icon.getPicture()));
			model.addAttribute("icon", icon);
		}
		// Banner
		MallPicture banner = pictureService.findOne(itemNumber, MallPictureType.BANNER);
		if (banner != null) {
			banner.setPicture(getImageFullPath(banner.getPicture()));
			model.addAttribute("banner", banner);
		}
		// 额外限制
		MallExtendLimit limit = limitService.findOne(itemNumber);
		if(limit != null){
			String startTime = MbayDateFormat.formatDateTime(limit.getStartTime(), "HH:mm");
			String endTime = MbayDateFormat.formatDateTime(limit.getEndTime(), "HH:mm");
			model.addAttribute("limit", limit);
			model.addAttribute("startHour", startTime.split(":")[0]);
			model.addAttribute("startMinute", startTime.split(":")[1]);
			model.addAttribute("endHour", endTime.split(":")[0]);
			model.addAttribute("endMinute", endTime.split(":")[1]);
		}
		
		// 获取商城
		List<DuiBaRelationShip> relationList = relationService.findList(ChannelContext.getSessionChannel().getUserNumber(), null);
		model.addAttribute("relationList", relationList);
		return EXCHANGE_ITEM_UPDATE;
	}
	
	/**
	 * 优惠券券码新增页面
	 * 
	 * @param itemNumber
	 * @param model
	 * @return
	 */
	@RequestMapping("/exchangeItem/toCouponTicketAdd")
	@Token(save = true)
	public String toCouponTicketAdd(String itemNumber, Model model) {
		MallExchangeItem bean = itemService.findOne(itemNumber);
		model.addAttribute("bean", bean);
		return COUPON_TICKET_ADD;
	}
	
	/**
	 * 优惠券券码列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/exchangeItem/couponTicketList")
	@Token(save = true)
	public String couponTicketList(String itemNumber, String ticket, PageInfo pageinfo, Model model) {
		MallExchangeItem item = itemService.findOne(itemNumber);
		List<MallCouponTicket> list = ticketService.findList(itemNumber, ticket, pageinfo);
		model.addAttribute("audit", item.getAudit());
		model.addAttribute("itemNumber", itemNumber);
		model.addAttribute("ticket", ticket);
		model.addAttribute("list", list);
		model.addAttribute("pageinfo", pageinfo);
		return COUPON_TICKET_LIST;
	}
	
	/**
	 * 保存兑换项
	 * 
	 * @param bean
	 * @param br
	 * @param detailOne
	 * @param detailTwo
	 * @param detailThree
	 * @param detailFour
	 * @param detailFive
	 * @param thumbnail
	 * @param icon
	 * @param banner
	 * @param dayLimit
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exchangeItem/addExchangeItem")
	@Token(reset = true)
	public String addExchangeItem(@Valid MallExchangeItem bean, BindingResult br,
			@RequestParam(value = "detailOne", required = true) MultipartFile detailOne,
			@RequestParam(value = "detailTwo", required = false) MultipartFile detailTwo,
			@RequestParam(value = "detailThree", required = false) MultipartFile detailThree,
			@RequestParam(value = "detailFour", required = false) MultipartFile detailFour,
			@RequestParam(value = "detailFive", required = false) MultipartFile detailFive,
			@RequestParam(value = "thumbnail", required = true) MultipartFile thumbnail,
			@RequestParam(value = "icon", required = true) MultipartFile icon,
			@RequestParam(value = "banner", required = false) MultipartFile banner,
			@RequestParam(value = "dayLimit", required = false) String dayLimit,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			Model model, HttpServletRequest request) {
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toExchangeItemAdd(bean.getType(), model);
		}
		
		DateTime now = DateTime.now();
		String number = MbayDateFormat.formatDateTime(now, DatePattern.yyyyMMdd);
		int nextid = utilService.getNextIndex(MallExchangeItem.class);
		bean.setItemNumber(number + (SerialSeed.Event.MALL) + (SerialSeed.CARDINAL_NUMBER + nextid));
		bean.setCreateTime(now);
		bean.setUserNumber(ChannelContext.getSessionChannel().getUserNumber());
		bean.setStatus(MallStatus.OFF);
		bean.setAudit(MallAudit.NON);
		
		// 详情图1
		if (detailOne != null && detailOne.getSize() > 0) {
			addPicture(detailOne, bean.getItemNumber(), MallPictureType.DETAIL);
		} else {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toExchangeItemAdd(bean.getType(), model);
		}
		
		// 缩略图
		if (thumbnail != null && thumbnail.getSize() > 0) {
			addPicture(thumbnail, bean.getItemNumber(), MallPictureType.THUMBNAIL);
		} else {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toExchangeItemAdd(bean.getType(), model);
		}
		
		// 图标
		if (icon != null && icon.getSize() > 0) {
			addPicture(icon, bean.getItemNumber(), MallPictureType.ICON);
		} else {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toExchangeItemAdd(bean.getType(), model);
		}
		
		// 详情图2
		if (detailTwo != null && detailTwo.getSize() > 0) {
			addPicture(detailTwo, bean.getItemNumber(), MallPictureType.DETAIL);
		}
		// 详情图3
		if (detailThree != null && detailThree.getSize() > 0) {
			addPicture(detailThree, bean.getItemNumber(), MallPictureType.DETAIL);
		}
		// 详情图4
		if (detailFour != null && detailFour.getSize() > 0) {
			addPicture(detailFour, bean.getItemNumber(), MallPictureType.DETAIL);
		}
		// 详情图5
		if (detailFive != null && detailFive.getSize() > 0) {
			addPicture(detailFive, bean.getItemNumber(), MallPictureType.DETAIL);
		}
		
		// banner
		if (banner != null && banner.getSize() > 0) {
			addPicture(banner, bean.getItemNumber(), MallPictureType.BANNER);
		}
		
		// 判断是否需要开启额外兑换限制
		if (bean.getExtendLimit().equals(EnableState.ENABLED)) {
			addExtendLimit(bean.getItemNumber(), dayLimit, startTime, endTime);
		}
		
		itemService.add(bean);
		
		if (bean.getType().equals(MallItemType.COUPON)) {
			TokenProcessor.getInstance().saveToken(request);
			return toCouponTicketAdd(bean.getItemNumber(), model);
		}
		return REDIRECT_EXCHANGE_ITEM_LIST;
		
	}
	
	/**
	 * 更新兑换项
	 * 
	 * @param bean
	 * @param br
	 * @param detailOne
	 * @param detailTwo
	 * @param detailThree
	 * @param detailFour
	 * @param detailFive
	 * @param thumbnail
	 * @param icon
	 * @param banner
	 * @param dayLimit
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exchangeItem/updateExchangeItem")
	@Token(reset = true)
	public String updateExchangeItem(@Valid MallExchangeItem bean, BindingResult br,
			@RequestParam(value = "detailOne", required = true) MultipartFile detailOne,
			@RequestParam(value = "detailTwo", required = false) MultipartFile detailTwo,
			@RequestParam(value = "detailThree", required = false) MultipartFile detailThree,
			@RequestParam(value = "detailFour", required = false) MultipartFile detailFour,
			@RequestParam(value = "detailFive", required = false) MultipartFile detailFive,
			@RequestParam(value = "thumbnail", required = true) MultipartFile thumbnail,
			@RequestParam(value = "icon", required = true) MultipartFile icon,
			@RequestParam(value = "banner", required = false) MultipartFile banner,
			@RequestParam(value = "dayLimit", required = false) String dayLimit,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "delTwo", required = true) Boolean delTwo,
			@RequestParam(value = "delThree", required = true) Boolean delThree,
			@RequestParam(value = "delFour", required = true) Boolean delFour,
			@RequestParam(value = "delFive", required = true) Boolean delFive,
			Model model, HttpServletRequest request) {
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toExchangeItemUpdate(model, bean.getItemNumber());
		}
		
		bean.setAudit(MallAudit.CHANGE);
		
		List<MallPicture> details = pictureService.findDetails(bean.getItemNumber());
		// 详情图1
		if (detailOne != null && detailOne.getSize() > 0) {
			updatePicture(0, details, detailOne, bean.getItemNumber());
		}
		
		// 缩略图
		if (thumbnail != null && thumbnail.getSize() > 0) {
			updatePicture(thumbnail, bean.getItemNumber(), MallPictureType.THUMBNAIL);
		}
		
		// 图标
		if (icon != null && icon.getSize() > 0) {
			updatePicture(icon, bean.getItemNumber(), MallPictureType.ICON);
		}
		
		// 详情图2
		if (delTwo) {
			deletePicture(1, details);
		} else if (detailTwo != null && detailTwo.getSize() > 0) {
			updatePicture(1, details, detailTwo, bean.getItemNumber());
		}
		// 详情图3
		if (delThree) {
			deletePicture(2, details);
		} else if (detailThree != null && detailThree.getSize() > 0) {
			updatePicture(2, details, detailThree, bean.getItemNumber());
		}
		// 详情图4
		if (delFour) {
			deletePicture(3, details);
		} else if (detailFour != null && detailFour.getSize() > 0) {
			updatePicture(3, details, detailFour, bean.getItemNumber());
		}
		// 详情图5
		if (delFive) {
			deletePicture(4, details);
		} else if (detailFive != null && detailFive.getSize() > 0) {
			updatePicture(4, details, detailFive, bean.getItemNumber());
		}
		
		// banner
		if (banner != null && banner.getSize() > 0) {
			updatePicture(banner, bean.getItemNumber(), MallPictureType.BANNER);
		}
		
		// 判断是否需要开启额外兑换限制
		if (bean.getExtendLimit().equals(EnableState.ENABLED)) {
			updateExtendLimit(bean.getItemNumber(), dayLimit, startTime, endTime);
		}
		
		itemService.update(bean);
		
		// 只有上架状态下更改信息才发送邮件
		if (bean.getType().equals(MallStatus.ON)) {
			sendMail("商城商品变更通知", "变更", bean.getItemNumber());
		}
		
		return REDIRECT_EXCHANGE_ITEM_LIST;
	}
	
	/**
	 * 保存券码
	 * 
	 * @param bean
	 * @param br
	 * @param ticketFile
	 * @param startTime
	 * @param endTime
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/exchangeItem/addCouponTicket")
	@Token(reset = true)
	public String addCouponTicket(@Valid MallCouponTicket bean, BindingResult br,
			@RequestParam(value = "tickets", required = true) String tickets,
			@RequestParam(value = "eventstarttime", required = true) String eventstarttime,
			@RequestParam(value = "eventendtime", required = true) String eventendtime,
			Model model, HttpServletRequest request) {
		if (br.hasErrors()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toCouponTicketAdd(bean.getItemNumber(), model);
		}
		
		if (StringUtils.isEmpty(tickets)) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, Error.FORM_VALIDATE_ERROR);
			TokenProcessor.getInstance().saveToken(request);
			return toCouponTicketAdd(bean.getItemNumber(), model);
		}
		
		String starttime = request.getParameter("eventstarttime") + " 00:00:00";
		String endtime = request.getParameter("eventendtime") + " 23:59:59";
		bean.setStartTime(MbayDateFormat.stringToTime(starttime));
		bean.setEndTime(MbayDateFormat.stringToTime(endtime));
		DateTime now = DateTime.now();
		bean.setCreateTime(now);
		
		JSONObject json = JSONObject.fromObject(tickets);
		Iterator it = json.keys();
		int remainder = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			bean.setTicket(key);
			String value = json.getString(key);
			bean.setPassword(value);
			ticketService.add(bean);
			remainder++;
		}
		
		itemService.changeRemainder(bean.getItemNumber(), remainder);
		
		return REDIRECT_EXCHANGE_ITEM_LIST;
	}
	
	/**
	 * 显示上传文件内容
	 * 
	 * @param ticketFile
	 * @return
	 */
	@RequestMapping("/exchangeItem/showTicket")
	@ResponseBody
	public JSONObject showTicket(@RequestParam(value = "ticketFile", required = true) MultipartFile ticketFile) {
		JSONObject json = new JSONObject();
		String type = "txt";
		String fileName = ticketFile.getOriginalFilename();
		String extendtion = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
		if (!type.equals(extendtion)) {
			json.put("success", false);
			json.put("value", "格式不对,请重新上传");
			return json;
		}
		try {
			InputStreamReader read = new InputStreamReader(ticketFile.getInputStream(), "utf-8");// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String text = null;
			JSONObject json_value = new JSONObject();
			int num = 0;
			while ((text = bufferedReader.readLine()) != null) {
				String ticket = text.split("\t")[0];
				int length = ticket.length();
				String password = text.substring(length + 1, text.length());
				json_value.put(ticket, password);
				num++;
			}
			read.close();
			json.clear();
			json.put("success", true);
			json.put("value", json_value.toString());
			json.put("num", num);
			return json;
		} catch (Exception e) {
			LOGGER.error("文件上传失败!", e.fillInStackTrace());
		}
		json.clear();
		json.put("success", false);
		json.put("value", "上传失败,请重新上传");
		return json;
	}
	
	/**
	 * 删除兑换项
	 * 
	 * @param itemNumber
	 * @return
	 */
	@RequestMapping("/exchangeItem/del")
	@ResponseBody
	public ExecuteResult del(String itemNumber) {
		MallExchangeItem item = itemService.findOne(itemNumber);
		// 如果是之前已经审核通过的数据不会真的删除,而是修改状态,否则直接从数据库中删除
		if (item.getAudit().equals(MallAudit.NON)) {
			return itemService.del(itemNumber);
		} else if (item.getAudit().equals(MallAudit.COMPLETE)) {
			ExecuteResult result = itemService.changeAudit(itemNumber, MallAudit.DELETE);
			if (result.isSuccess()) {
				sendMail("商城商品删除通知", "删除", itemNumber);
			}
			return result;
		}
		return new ExecuteResult(false, "删除失败");
	}
	
	/**
	 * 删除券码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/exchangeItem/delTicket")
	@ResponseBody
	public ExecuteResult delTicket(String itemNumber, int id) {
		MallExchangeItem item = itemService.findOne(itemNumber);
		// 如果是之前已经审核通过的数据不会真的删除,而是修改状态,否则直接从数据库中删除
		if (item.getAudit().equals(MallAudit.NON)) {
			boolean result = ticketService.del(id);
			if(result){
				return ExecuteResult.successExecute;
			}
		}
		return new ExecuteResult(false, "删除失败");
	}
	
	/**
	 * 上/下架
	 * 
	 * @param itemNumber
	 * @return
	 */
	@RequestMapping("/exchangeItem/changeStatus")
	@ResponseBody
	public ExecuteResult changeStatus(String itemNumber, MallStatus status) {
		ExecuteResult result = itemService.changeStatus(itemNumber, status);
		if (result.isSuccess()) {
			sendMail("商城商品" + status.getValue() + "通知", status.getValue(), itemNumber);
		}
		return result;
	}
	
	/**
	 * 批量上架
	 * 
	 * @param itemNumbers
	 * @return
	 */
	@RequestMapping("/exchangeItem/shelfAll")
	@ResponseBody
	public ExecuteResult shelfAll(String itemNumbers) {
		if (!itemNumbers.contains(",")) {
			ExecuteResult result = itemService.changeStatus(itemNumbers, MallStatus.ON);
			if (result.isSuccess()) {
				sendMail("商城商品上架通知", "上架", itemNumbers);
			}
			return result;
		}
		
		List<String> list = new ArrayList<String>();
		int success = 0;
		String[] arrs = itemNumbers.split(",");
		for (int i = 0; i < arrs.length; i++) {
			String arr = arrs[i];
			ExecuteResult result = itemService.changeStatus(arr, MallStatus.ON);
			if (result.isSuccess()) {
				list.add(arr);
				success++;
			}
		}
		sendMail("商城商品上架通知", "上架", list);
		return new ExecuteResult(true, "上架成功,已成功上架" + success + "个");
	}
	
	/**
	 * 批量删除
	 * 
	 * @param itemNumbers
	 * @return
	 */
	@RequestMapping("/exchangeItem/delAll")
	@ResponseBody
	public ExecuteResult delAll(String itemNumbers) {
		if (!itemNumbers.contains(",")) {
			MallExchangeItem item = itemService.findOne(itemNumbers);
			// 如果是之前已经审核通过的数据不会真的删除,而是修改状态,否则直接从数据库中删除
			if (item.getAudit().equals(MallAudit.NON)) {
				return itemService.del(itemNumbers);
			} else if (item.getAudit().equals(MallAudit.COMPLETE)) {
				ExecuteResult result = itemService.changeAudit(itemNumbers, MallAudit.DELETE);
				if (result.isSuccess()) {
					sendMail("商城商品删除通知", "删除", itemNumbers);
				}
				return result;
			}
		}
		
		List<String> list = new ArrayList<String>();
		int success = 0;
		String[] arrs = itemNumbers.split(",");
		for (int i = 0; i < arrs.length; i++) {
			String arr = arrs[i];
			MallExchangeItem item = itemService.findOne(arr);
			// 如果是之前已经审核通过的数据不会真的删除,而是修改状态,否则直接从数据库中删除
			if (item.getAudit().equals(MallAudit.NON)) {
				itemService.del(arr);
			} else if (item.getAudit().equals(MallAudit.COMPLETE)) {
				ExecuteResult result = itemService.changeAudit(arr, MallAudit.DELETE);
				if (result.isSuccess()) {
					list.add(arr);
				}
			}
			success++;
		}
		sendMail("商城商品删除通知", "删除", list);
		return new ExecuteResult(true, "删除成功,已成功删除" + success + "个");
	}
	
	/**
	 * 获取缩略图
	 * 
	 * @param itemNumber
	 * @return
	 */
	@RequestMapping("/exchangeItem/getThumbnail")
	public String getThumbnail(String itemNumber, HttpServletResponse response) {
		MallPicture picture = pictureService.findOne(itemNumber, MallPictureType.THUMBNAIL);
		if (picture != null) {
			String location = picture.getPicture();
			byte[] result = fsClient.downloadFile(location);
			try {
				response.getOutputStream().write(result);
			} catch (IOException e) {
				LOGGER.error("向前台传输缩略图失败,itemNumber:" + itemNumber);
			}
		}
		return null;
	}
	
	/**
	 * 手机预览
	 * 
	 * @param itemNumber
	 * @return
	 */
	@RequestMapping("/exchangeItem/preview")
	public String preview(String itemNumber, Model model) {
		// 兑换项
		MallExchangeItem bean = itemService.findOne(itemNumber);
		model.addAttribute("bean", bean);
		// 详细图
		List<MallPicture> pictures = pictureService.findDetails(itemNumber);
		List<MallPicture> details = new ArrayList<MallPicture>();
		for (MallPicture picture : pictures) {
			String image = picture.getPicture();
			picture.setPicture(getImageFullPath(image));
			details.add(picture);
		}
		model.addAttribute("details", details);
		return EXCHANGE_ITEM_PREVIEW;
	}
	
	/**
	 * 上传图片
	 * 
	 * @param file
	 * @param itemNumber
	 * @param type
	 */
	private void addPicture(MultipartFile file, String itemNumber, MallPictureType type) {
		String image = fsClient.uploadFile(file);
		MallPicture picture = new MallPicture();
		picture.setItemNumber(itemNumber);
		picture.setPicture(image);
		picture.setType(type);
		picture.setCreateTime(DateTime.now());
		pictureService.add(picture);
	}
	
	/**
	 * 修改图片(除了详细图之外)
	 * 
	 * @param file
	 * @param itemNumber
	 * @param type
	 */
	private void updatePicture(MultipartFile file, String itemNumber, MallPictureType type) {
		MallPicture picture = pictureService.findOne(itemNumber, type);
		if (picture != null) {
			// 删除原有图片
			fsClient.deleteFile(picture.getPicture());
			String image = fsClient.uploadFile(file);
			picture.setPicture(image);
			pictureService.update(picture);
		} else {
			addPicture(file, itemNumber, type);
		}
	}
	
	/**
	 * 修改图片(详细图)
	 * 
	 * @param file
	 * @param itemNumber
	 * @param type
	 */
	private void updatePicture(int size, List<MallPicture> details, MultipartFile file, String itemNumber) {
		if (details.size() > size && details.get(size) != null) {
			MallPicture picture = details.get(size);
			// 删除原有图片
			fsClient.deleteFile(picture.getPicture());
			String image = fsClient.uploadFile(file);
			picture.setPicture(image);
			pictureService.update(picture);
		} else {
			addPicture(file, itemNumber, MallPictureType.DETAIL);
		}
	}
	
	/**
	 * 删除图片(详细图)
	 * 
	 * @param itemNumber
	 * @param type
	 */
	private void deletePicture(int size, List<MallPicture> details) {
		if (details.size() > size && details.get(size) != null) {
			MallPicture picture = details.get(size);
			// 删除原有图片
			fsClient.deleteFile(picture.getPicture());
			pictureService.del(picture.getId());
		}
	}
	
	/**
	 * 新建额外兑换限制
	 * 
	 * @param itemNumber
	 * @param type
	 */
	private void addExtendLimit(String itemNumber, String dayLimit, String startTime, String endTime) {
		MallExtendLimit limit = new MallExtendLimit();
		limit.setItemNumber(itemNumber);
		limit.setDayLimit(dayLimit);
		limit.setStartTime(MbayDateFormat.stringToHHmm(startTime));
		limit.setEndTime(MbayDateFormat.stringToHHmm(endTime));
		limitService.add(limit);
	}
	
	/**
	 * 更新额外兑换限制
	 * 
	 * @param itemNumber
	 * @param type
	 */
	private void updateExtendLimit(String itemNumber, String dayLimit, String startTime, String endTime) {
		MallExtendLimit limit = limitService.findOne(itemNumber);
		if (limit != null) {
			limit.setDayLimit(dayLimit);
			limit.setStartTime(MbayDateFormat.stringToHHmm(startTime));
			limit.setEndTime(MbayDateFormat.stringToHHmm(endTime));
			limitService.update(limit);
		} else {
			addExtendLimit(itemNumber, dayLimit, startTime, endTime);
		}
	}
	
	/**
	 * 返回图片完整地址
	 * 
	 * @param picture
	 * @return
	 */
	private String getImageFullPath(String picture) {
		if (!StringUtils.isEmpty(picture)) {
			FDFSFileInfo info = (FDFSFileInfo) fsClient.getFileInfo(picture);
			return info.getFullPath();
		}
		return null;
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param to
	 *            接收人
	 * @param subject
	 *            主题
	 * @param type
	 *            通知类型
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void sendMail(String subject, String type, Object object) {
		String htmlText = "";
		List<String> list = null;
		if (object instanceof List) {
			list = (List<String>) object;
		} else {
			list = new ArrayList<String>();
			list.add((String) object);
		}
		
		for (String itemNumber : list) {
			MallExchangeItem bean = itemService.findOne(itemNumber);
			htmlText += "<tr><td>应用名称:</td><td>" + bean.getMall().getName() + "</td></tr>"
					+ "<tr><td>通知类型:</td><td>" + type + "</td></tr>"
					+ "<tr><td>商品编号:</td><td>" + bean.getItemNumber() + "</td></tr>"
					+ "<tr><td>商品名称:</td><td>" + bean.getTitle() + "</td></tr>"
					+ "<tr><td colspan='2'></td></tr>";
		}
		
		MbayMail.sendMail("duiba@mbqianbao.com", subject, "<html><table><tr><td>邮件主题:</td>"
				+ "<td>" + subject + "</td></tr><tr><td colspan='2'>内容:</td></tr>"
				+ htmlText + "</table></html>");
	}
}
