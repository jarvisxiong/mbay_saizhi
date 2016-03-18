package org.sz.mbay.channel.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.constant.Globals;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.component.excel.EXCELType;
import org.sz.mbay.channel.component.excel.POIExcel;
import org.sz.mbay.channel.context.ChannelContext;
import org.sz.mbay.channel.costant.error.CustomerServerError;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.customer.bean.BatchChargeInfo;
import org.sz.mbay.customer.bean.BatchChargeItem;
import org.sz.mbay.customer.bean.BatchChargeMobile;
import org.sz.mbay.customer.bean.BatchChargeStrategy;
import org.sz.mbay.customer.enums.BatchChargeMethod;
import org.sz.mbay.customer.qo.BatchChargeMobileForm;
import org.sz.mbay.customer.service.CustomerServerService;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;

import net.sf.json.JSONObject;

/**
 * 企业客户管理
 * 
 * @author meibei-hrain
 * 		
 */
@Controller
@RequestMapping("customerserver")
public class CustomerServerAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerServerAction.class);
	// /批充信息列表
	public static final String SERVER_MANAGER = "customer/servermanager";
	// /批充信息添加
	public static final String BATCHCHARGE_ADD = "customer/add";
	// 重定向至批充手机信息
	public static final String REDIRECT_BATCH_RECHARGE_MOBILEINFO = "redirect:/customerserver/batch_mobileinfo.mbay";
	// 批充手机信息
	public static final String BATCH_MOBILE_INFO = "customer/batch_mobile_info";
	public static final String BATCHCHARGE_ADD_SUCCESS = "redirect:/customer/servermanager.mbay";
	public static final String BATCHCHARGE_CONFIG = "customer/charge/chargeconfig";
	public static final String BATCH_CHARGE_ITEM = "customer/charge/batch_charge_items";
	// 重定向到批充提交成功
	public static final String REDIRECT_BATCH_CHARGE_SUCCESS = "redirect:/customerserver/batch_charge_success.mbay";
	// /批量充值提交成功
	public static final String BATCH_CHARGE_SUCCESS = "customer/charge/charge_success";
	
	@Autowired
	CustomerServerService serverservice;
	
	@Autowired
	UserAccountService assetsservice;
	
	@Autowired
	TrafficRechargeService trafficservice;
	
	/**
	 * 批充管理
	 * 
	 * @param info
	 * @param pageinfo
	 * @param model
	 * @return
	 */
	@RequestMapping("servermanager")
	public String servermanager(BatchChargeInfo info, Model model,
			PageInfo pageinfo) {
		info.setUsernumber(ChannelContext.getSessionChannel().getUserNumber());
		List<BatchChargeInfo> items = this.serverservice
				.findAllBatchChargeInfo(info, pageinfo);
		model.addAttribute("chargeinfoitems", items);
		model.addAttribute("info", info);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return SERVER_MANAGER;
	}
	
	/**
	 * 填写批充信息
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@CacheControl(policy = { CachePolicy.NO_CACHE })
	@RequestMapping("batchcharge/fill")
	@Token(save = true)
	public String fillBatchCharge(Model model, HttpServletRequest request) {
		// 获取当前月号
		int dayofmoth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		model.addAttribute("dayofmoth", dayofmoth);
		return BATCHCHARGE_ADD;
	}
	
	/**
	 * 提交批充信息 验证并解析文件
	 * 
	 * @param chargetype
	 * @param traffictype
	 * @param request
	 * @param redAttr
	 * @param excelfile
	 *            上传的文件
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("batchcharge/add")
	@Token(reset = true)
	public String addBatchChargeInfo(BatchChargeMethod chargetype,
			HttpServletRequest request,
			RedirectAttributes redAttr, MultipartFile excelfile, Model model) {
		String filename = excelfile.getOriginalFilename();
		String etype = filename.substring(filename.indexOf(".") + 1);
		String[] packagests = request.getParameterValues("packageid");// 策略所对应的流量包
		String[] supportOperator = request.getParameterValues("operator");// 所支持的运营商
		if (packagests.length == 0 || supportOperator.length == 0
				|| packagests.length != supportOperator.length) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "策略选择有误,请重试!");
			TokenProcessor.getInstance().saveToken(request);// 重置token
			return BATCHCHARGE_ADD;
		}
		String period = request.getParameter("period");// 定期充值日
		String name = request.getParameter("name");// 活动名称
		List<BatchChargeMobile> chargeMobiles = new ArrayList<BatchChargeMobile>();
		Map<String, String> map = new HashMap<String, String>();
		try {
			EXCELType type = EXCELType.valueOf(etype.toUpperCase());
			Iterator<Row> rowIterator = POIExcel
					.readExcel(excelfile.getInputStream(), type);
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Cell cell0 = row.getCell(0);
				String mobile = "";
				switch (cell0.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						DecimalFormat df = new DecimalFormat("#");
						mobile = df.format((cell0.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						mobile = cell0.getStringCellValue();
						break;
				}
				if (StringUtils.isEmpty(mobile)) {
					break;
				}
				// 正则表达式验证合格手机号
				// 查询手机号H码信息
				HcodeInfo codeinfo = MbayHcode.getHcodeInfo(mobile);
				if (!RegExp.mobile.matcher(mobile).matches()
						|| codeinfo == null) {
					model.addAttribute(PUBLIC_MSG_NOTE_KEY,
							"号码" + mobile + "不是正确的手机号");
					TokenProcessor.getInstance().saveToken(request);// 重置token
					return BATCHCHARGE_ADD;
				}
				// 判断是否有重复手机号
				if (StringUtils.isEmpty(map.get(mobile))) {
					map.put(mobile, mobile);
				} else {
					model.addAttribute(PUBLIC_MSG_NOTE_KEY,
							"出现重复号码,号码为:" + mobile);
					TokenProcessor.getInstance().saveToken(request);// 重置token
					return BATCHCHARGE_ADD;
				}
				BatchChargeMobile chargemobile = new BatchChargeMobile();
				chargemobile.setMobile(mobile);
				// 类型
				chargemobile.setOperator(
						OperatorType.valueOf(codeinfo.getOperator()));
				// 根据手机号类型查询对应的流量包
				int operatorid = codeinfo.getOperator();
				// 判断是否已经选中对应手机号的流量包
				Boolean flag = false;
				for (int i = 0; i < supportOperator.length; i++) {
					int value = Integer.valueOf(supportOperator[i]);
					if (operatorid == value) {
						flag = true;
					}
				}
				if (!flag) {
					model.addAttribute(PUBLIC_MSG_NOTE_KEY,
							"没有选中对应手机号的流量包!手机号:" + mobile + ",运营商:"
									+ OperatorType
											.valueOf(codeinfo.getOperator())
											.getValue());
					TokenProcessor.getInstance().saveToken(request);// 重置token
					return BATCHCHARGE_ADD;
				}
				chargeMobiles.add(chargemobile);
			}
		} catch (Exception e) {
			LOGGER.error("批量导入异常", e.fillInStackTrace());
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, "文件解析失败，请检查文件格式是否合法");
			TokenProcessor.getInstance().saveToken(request);// 重置token
			return BATCHCHARGE_ADD;
		}
		map.clear();
		BatchChargeInfo bci = new BatchChargeInfo();
		bci.setUsernumber(ChannelContext.getSessionChannel().getUserNumber());
		bci.setMobilenum(chargeMobiles.size());
		bci.setName(name);
		bci.setChargemethod(chargetype);
		bci.setCreatetime(new Timestamp(System.currentTimeMillis()));
		// 判断是否定期充值
		if (BatchChargeMethod.PERIOD_CHARGE.equals(bci.getChargemethod())) {
			bci.setRegulartime(Integer.valueOf(period));
		}
		bci.setBatchchargemobiles(chargeMobiles);
		// 添加批充信息-流量包关联信息
		List<BatchChargeStrategy> strategys = new ArrayList<BatchChargeStrategy>();
		for (int i = 0; i < supportOperator.length; i++) {
			BatchChargeStrategy strategy = new BatchChargeStrategy();
			int operatorid = Integer.valueOf(supportOperator[i]);
			strategy.setOperator(OperatorType.valueOf(operatorid));
			TrafficPackage trafficPackage = new TrafficPackage();
			trafficPackage.setId(Integer.valueOf(packagests[i]));
			strategy.setTrafficpackage(trafficPackage);
			strategys.add(strategy);
		}
		bci.setStrategys(strategys);
		ExecuteResult result = this.serverservice.addBatchChargeInfo(bci);
		if (!result.isSuccess()) {
			model.addAttribute(PUBLIC_MSG_NOTE_KEY, result.getMessage());
			TokenProcessor.getInstance().saveToken(request);// 重置token
			return BATCHCHARGE_ADD;
		}
		redAttr.addAttribute("batchid", result.getMessage());
		return REDIRECT_BATCH_RECHARGE_MOBILEINFO;
	}
	
	/**
	 * 批充手机号码信息
	 * 
	 * @param request
	 * @param model
	 * @param pageinfo
	 * @return
	 */
	@RequestMapping("batch_mobileinfo")
	public String batchRechrge_mobileinfo(
			BatchChargeMobileForm chargeMobileForm,
			Model model, PageInfo pageinfo) {
		chargeMobileForm.setUsernumber(
				ChannelContext.getSessionChannel().getUserNumber());
		BatchChargeInfo chargeinfo = this.serverservice.findBatchChargeInfo(
				chargeMobileForm.getBatchid(),
				ChannelContext.getSessionChannel().getUserNumber());
		if (chargeinfo == null) {
			throw new ServiceException(
					CustomerServerError.NONE_EXIST_BATCHCHARGE);
		}
		List<BatchChargeMobile> mobiles = this.serverservice
				.findBatchChargeMobileInfo(chargeMobileForm, pageinfo);
		String sms_template = MbaySMS.getSMSContent(
				SMSType.CUSTOMER_SERVER);
		;
		model.addAttribute("sms_template", sms_template);
		model.addAttribute("chargeinfo", chargeinfo);
		model.addAttribute("batchchargemobiles", mobiles);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		model.addAttribute("chargeMobileForm", chargeMobileForm);
		return BATCH_MOBILE_INFO;
	}
	
	/**
	 * 批充
	 * 
	 * @param id
	 * @param sms
	 * @param model
	 * @return
	 */
	@RequestMapping("batchcharge/charge")
	@ResponseBody
	public JSONObject batchCharge(@RequestParam("id") int id,
			@RequestParam("sms") boolean sms, Model model) {
		String usernumber = ChannelContext.getSessionChannel().getUserNumber();
		ExecuteResult result = this.serverservice.batchCharge(id, sms,
				usernumber);
		return JSONObject.fromObject(result);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("batch_charge_success")
	public String batchChargeSuccess() {
		return BATCH_CHARGE_SUCCESS;
	}
	
	/**
	 * 获取充值记录
	 * 
	 * @param id
	 * @param pageinfo
	 * @param model
	 * @return
	 */
	@RequestMapping("batchcharge/record/charge_items")
	public String chargeItems(@RequestParam("id") int id, PageInfo pageinfo,
			Model model) {
		BatchChargeInfo chargeinfo = this.serverservice.findBatchChargeInfo(id,
				ChannelContext.getSessionChannel().getUserNumber());
		List<BatchChargeItem> items = this.serverservice.findBatchChargeItems(
				id, pageinfo);
		model.addAttribute("chargeinfo", chargeinfo);
		model.addAttribute("chargeitems", items);
		model.addAttribute(Globals.PAGEINFO_KEY, pageinfo);
		return BATCH_CHARGE_ITEM;
	}
	
	/**
	 * 获取批充Excel号码
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "batchcharge/checkmobile",
			method = RequestMethod.GET)
	public void getFile(@RequestParam("id") int id,
			HttpServletResponse response) {
		try {
			String url = this.serverservice.getChargeInfoExcelURL(id);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=mobilefile.xls");
			File file = new File(url);
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			LOGGER.info("getFile error", ex.fillInStackTrace());
		}
		
	}
	
	/**
	 * 增加一条批充手机号信息
	 * 
	 * @param mobile
	 *            手机实体
	 * @param model
	 * @param redAttr
	 * @return
	 */
	@RequestMapping("batchcharge/addMobile")
	public String addphonenumber(BatchChargeMobile mobile,
			RedirectAttributes redAttr) {
		// 查询批充信息详情订单是否存在
		if (!this.serverservice.isExistingBatchCharge(mobile.getBatchid(),
				ChannelContext.getSessionChannel().getUserNumber())) {
			throw new ServiceException(
					CustomerServerError.NONE_EXIST_BATCHCHARGE);
		}
		this.serverservice.addBatchChargeMobile(mobile);
		redAttr.addAttribute("batchid", mobile.getBatchid());
		return REDIRECT_BATCH_RECHARGE_MOBILEINFO;
	}
	
	/**
	 * 删除批充中指定手机号
	 * 
	 * @param batchid
	 * @param mobile
	 * @return
	 */
	@RequestMapping("deletemobile")
	@ResponseBody
	public JSONObject deletChargeMobile(@RequestParam("batchid") int batchid,
			@RequestParam("mobile") String mobile,
			@RequestParam("operator") OperatorType operator) {
		if (!this.serverservice.isExistingBatchCharge(batchid,
				ChannelContext.getSessionChannel().getUserNumber())) {
			return JSONObject.fromObject(new ExecuteResult(false, "不存在的批充信息！"));
		}
		boolean b = this.serverservice.deleteChargeMobile(batchid, mobile,
				operator);
		ExecuteResult result = new ExecuteResult(b, "");
		return JSONObject.fromObject(result);
	}
	
}
