package org.sz.mbay.register.action.api;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.enums.Department;
import org.sz.mbay.channel.user.service.CertificateAuthService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.common.util.MbayDateFormat;

import net.sf.json.JSONObject;

/**
 * 注册接口
 * 
 * @author frank.zong
 * 		
 */
@Controller
@RequestMapping("register/api")
public class RegisterApiAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApiAction.class);
	
	@Autowired
	CertificateAuthService certificateService;
	@Autowired
	UserService userService;
	
	/**
	 * 创建用户
	 * 
	 * @param supNumber
	 *            邀请码
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public JSONObject create(@RequestParam(value = "supNumber") String supNumber,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password) {
		JSONObject json = new JSONObject();
		
		LOGGER.info("===========【请求格式验证 start】=========");
		if (StringUtils.isEmpty(supNumber) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			LOGGER.error("【参数不正确】 -> supNumber:{}; userName:{}; password:{}", supNumber, userName, password);
			json.put("status", false);
			json.put("data", "请求格式不正确");
			return json;
		}
		LOGGER.info("===========【请求格式验证 end】=========");
		
		LOGGER.info("===========【邀请码验证 start】=========");
		if (!userService.authExistUserByUserNumber(supNumber)) {
			LOGGER.error("【邀请码不存在】 -> supNumber:{}", supNumber);
			json.put("status", false);
			json.put("data", "邀请码不存在");
			return json;
		}
		LOGGER.info("===========【邀请码验证 end】=========");
		
		LOGGER.info("===========【用户名验证 start】=========");
		if (userService.isExistUserName(userName)) {
			LOGGER.error("【用户名已存在】 -> userName:{}", userName);
			json.put("status", false);
			json.put("data", "用户名已存在");
			return json;
		}
		LOGGER.info("===========【用户名验证 end】=========");
		
		LOGGER.info("===========【注册 start】=========");
		User user = new User();
		user.setSupnumber(supNumber);
		user.setUsername(userName);
		user.setPassword(password);
		user.setProperty(ChannelProperty.ENTERPRISE);
		try {
			user = userService.registerUser(user);
		} catch (Exception e) {
			LOGGER.error("【注册失败】 -> userName:{}", userName);
			json.put("status", false);
			json.put("data", "注册失败");
			return json;
		}
		json.put("status", true);
		JSONObject success = new JSONObject();
		success.put("userName", userName);
		success.put("createTime", MbayDateFormat.formatDateTime(DateTime.now(), "yyyy-MM-dd HH:mm:ss"));
		json.put("data", success);
		LOGGER.info("【注册成功】 -> result:{}", json.toString());
		LOGGER.info("===========【注册 end】=========");
		return json;
	}
	
	/**
	 * 完善信息
	 * 
	 * @param certificate
	 * @return
	 */
	@RequestMapping(value = "/perfect")
	@ResponseBody
	public JSONObject perfect(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "contactsName") String contactsName,
			@RequestParam(value = "department") String department,
			@RequestParam(value = "fixPhone") String fixPhone,
			@RequestParam(value = "contactsEmail") String contactsEmail,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "contactsPhone") String contactsPhone,
			@RequestParam(value = "license") String license,
			@RequestParam(value = "tax") String tax,
			@RequestParam(value = "code") String code) {
		JSONObject json = new JSONObject();
		
		LOGGER.info("===========【请求格式验证 start】=========");
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(contactsName)
				|| StringUtils.isEmpty(department) || StringUtils.isEmpty(fixPhone)
				|| StringUtils.isEmpty(contactsEmail) || StringUtils.isEmpty(address) 
				|| StringUtils.isEmpty(contactsPhone) || StringUtils.isEmpty(license)
				|| StringUtils.isEmpty(tax) || StringUtils.isEmpty(code)) {
			LOGGER.error("【参数不正确】 -> userName:{}; contactsName:{}; department:{}; fixPhone:{};"
					+ "contactsEmail:{}; address:{}; contactsPhone:{}; license:{}; tax:{}; code:{};",
					userName, contactsName, department,fixPhone, contactsEmail, 
					address, contactsPhone, license, tax, code);
			json.put("status", false);
			json.put("data", "请求格式不正确");
			return json;
		}
		//验证枚举类型参数
		try{
			int department_int = Integer.valueOf(department);
			if(department_int < 0 || department_int >= Department.values().length){
				throw new Exception();
			}
		}catch(Exception e){
			LOGGER.error("【枚举类型参数不正确】 -> department:{}", department);
			json.put("status", false);
			json.put("data", "请求格式不正确");
			return json;
		}
		LOGGER.info("===========【请求格式验证 end】=========");
		
		LOGGER.info("===========【用户名验证 start】=========");
		if (!userService.isExistUserName(userName)) {
			LOGGER.error("【用户名不存在】 -> userName:{}", userName);
			json.put("status", false);
			json.put("data", "用户名不存在");
			return json;
		}
		LOGGER.info("===========【用户名验证 end】=========");
		
		LOGGER.info("===========【装载对象信息 start】=========");
		int userid = userService.getIDByUserName(userName);
		ChannelInfo info = new ChannelInfo();
		info.setUserid(userid);
		info.setContactsname(contactsName);
		info.setDepartment(Department.values()[Integer.valueOf(department)]);
		info.setContactsEmail(contactsEmail);
		info.setAddress(address);
		info.setContactsphone(contactsPhone);
		info.setCreatetime(new Timestamp(System.currentTimeMillis()));
		info.setCopyLicense(license);
		info.setCopyTax(tax);
		info.setCopyCode(code);
		LOGGER.info("===========【装载对象信息 end】=========");
		
		LOGGER.info("===========【完善 start】=========");
		ExecuteResult result = certificateService.addChannelinfo(info);
		if (!result.isSuccess()) {
			LOGGER.error("【完善失败】 -> userName:{}", userName);
			json.put("status", false);
			json.put("data", "完善失败");
			return json;
		}
		json.put("status", true);
		JSONObject success = new JSONObject();
		success.put("contactsName", contactsName);
		success.put("contactsPhone", contactsPhone);
		success.put("contactsEmail", contactsEmail);
		json.put("data", success);
		LOGGER.info("【完善成功】 -> result:{}", json.toString());
		LOGGER.info("===========【完善 end】=========");
		return json;
	}
}
