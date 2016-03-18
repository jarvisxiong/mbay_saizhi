//package org.sz.mbay.sms.service.impl;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.sz.mbay.common.util.HttpSupport;
//import org.sz.mbay.sms.bean.Result;
//import org.sz.mbay.sms.enums.SmsServiceType;
//import org.sz.mbay.sms.service.HHMSMSService;
//import org.sz.mbay.sms.util.SmsErrorUtil;
//import org.sz.mbay.sms.util.SmsUtil;
//
//public class HHMSMSServiceImpl implements HHMSMSService {
//	// private static final String singleSmsUrl =
//	// "http://117.135.143.35/msg/HttpSendSM";
//	private static final String batchSmsUrl = "http://117.135.143.35/msg/HttpBatchSendSM";
//	private static final String account = "sh-qd087";
//	private static final String password = "qazWSXedc~";
//
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Override
//	public Result sendSMS(String mobiles, int smsId, String param) {
//		if (logger.isInfoEnabled()) {
//			logger.info("何辉美短信接口执行短信发送.号码：{},短信id{},短信参数:{}", mobiles, smsId,
//					param);
//		}
//		String content = SmsUtil
//				.getSMSContent(smsId, param, SmsServiceType.HHM);
//		if (logger.isInfoEnabled()) {
//			logger.info("短信发送内容：{}", content);
//		}
//		
//		try {
//			Map<String, String> params = new HashMap<>();
//			params.put("account", account);
//			params.put("pswd", password);
//			params.put("mobile", mobiles);
//			params.put("msg", content);
//			params.put("needstatus", "true");
//			String result = HttpSupport.connect(batchSmsUrl, params);
//			return parseResult(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new Result(false, "error", "请求出错!",
//				SmsServiceType.HHM.getValue());
//	}
//
//	private static Result parseResult(String result) throws IOException {
//		BufferedReader reader = new BufferedReader(new StringReader(result));
//		result = reader.readLine();
//		String[] rs = result.split(",");
//		if (rs.length == 2) {
//			String code;
//			if ((code = rs[1]).equals("0")) {
//				// 请求成功
//				return new Result(true, code, "短信请求成功!",
//						SmsServiceType.HHM.getValue());
//			}
//
//			String errorInfo = SmsErrorUtil.getHHMErrorInfo(code);
//			return new Result(false, code, errorInfo,
//					SmsServiceType.HHM.getValue());
//		}
//		return null;
//	}
//}
