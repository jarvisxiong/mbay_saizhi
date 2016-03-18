package org.sz.mbay.sms.service.impl;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.sz.mbay.base.context.SpringApplicationContext;
import org.sz.mbay.sms.bean.Callback;
import org.sz.mbay.sms.bean.Result;
import org.sz.mbay.sms.bean.TemplateSMS;
import org.sz.mbay.sms.bean.VoiceCode;
import org.sz.mbay.sms.context.YZXConfig;
import org.sz.mbay.sms.service.YZXSMSService;
import org.sz.mbay.sms.util.DateUtil;
import org.sz.mbay.sms.util.EncryptUtil;
import org.sz.mbay.sms.util.SSLHttpClient;
import org.sz.mbay.sms.util.YZXMapperUtil;

import com.google.gson.Gson;

/**
 * 
 * @ClassName: YZXSMSServiceImpl
 * 
 * @Description: 云之讯短信服务平台实现类
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年12月23日 上午10:28:31
 * 
 */

@SuppressWarnings("deprecation")
public class YZXSMSServiceImpl implements YZXSMSService {
	
	private static Logger logger = LoggerFactory
			.getLogger(YZXSMSServiceImpl.class);
	
	private YZXConfig config;// 封装了云之讯平台需要的一些常量和参数
	
	public YZXConfig getConfig() {
		if (config == null) {
			config = (YZXConfig) SpringApplicationContext.getBean("YZXConfig");
		}
		return config;
	}
	
	@Override
	public Result sendSMS(String mobiles, int smsId, String param) {
		logger.info("发送短信给：{},短信本地模板id为{},短信参数为{}", mobiles, smsId, param);
		// 得到本地短信模板和云之讯平台短信模板id映射
		String smsTemplateId = YZXMapperUtil.getInstance().getContent(
				smsId + "");
		// 得到请求的主机
		String msgUrl = getConfig().getHost() + getConfig().getSendSMSUrl();
		// MD5加密
		EncryptUtil encryptUtil = new EncryptUtil();
		// 时间戳
		String timestamp = DateUtil.dateToStr(new Date(),
				DateUtil.DATE_TIME_NO_SLASH);
		// 填充url
		msgUrl = fillUrl(msgUrl, timestamp, encryptUtil);
		logger.info("构建短信发送请求url成功：{}", msgUrl);
		// 短信模版对象
		TemplateSMS templateSMS = new TemplateSMS();
		templateSMS.setAppId(getConfig().getAppId());
		templateSMS.setTemplateId(smsTemplateId);
		templateSMS.setTo(mobiles);
		templateSMS.setParam(param);
		// json 串行化短信模版对象
		Gson gson = new Gson();
		String body = gson.toJson(templateSMS);
		body = "{\"templateSMS\":" + body + "}";
		logger.info("rest(json)方式请求云之讯的包体为：{}", body);
		// 请求对方
		String resultJson = doPostRequest("application/json", msgUrl, body,
				getConfig().getAccountSid(), timestamp, encryptUtil);
		return parseJosn(resultJson, "短信验证码", getConfig().getRequestSuccess());
	}
	
	/**
	 * 
	 * @param contentType
	 *            数据格式
	 * @param url
	 *            请求的url
	 * @param auth
	 *            认证信息
	 * @param body
	 *            请求包体
	 * @param encryptUtil
	 * @param timestamp
	 * @param timestamp2
	 * @return
	 */
	private String doPostRequest(String contentType, String url, String body,
			String accountSid, String timestamp, EncryptUtil encryptUtil) {
		HttpClient httpClient = null;
		String result = "";
		HttpPost httpPost = (HttpPost) getRequestModel(
				RequestMethod.POST, url);
		
		httpPost.setHeader("Accept", contentType);
		httpPost.setHeader("Content-Type", contentType + ";charset=utf-8");
		try {
			// 加密认证信息
			String src = accountSid + ":" + timestamp;
			String auth = encryptUtil.base64Encoder(src);
			logger.info("加密认证信息成功:{}", auth);
			httpPost.setHeader("Authorization", auth);
			BasicHttpEntity requestBody = new BasicHttpEntity();
			requestBody.setContent(new ByteArrayInputStream(body
					.getBytes("UTF-8")));
			requestBody.setContentLength(body.getBytes("UTF-8").length);
			httpPost.setEntity(requestBody);
			
			httpClient = getDefaultHttpClient();
			HttpResponse response = null;
			
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("doPostRequest", e.fillInStackTrace());
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			logger.error("doPostRequest", e.fillInStackTrace());
		} finally {
			if (httpClient != null) {
				try {
					((Closeable) httpClient).close();
				} catch (IOException e) {
					logger.error("httpClient关闭异常!", e.fillInStackTrace());
				}
			}
		}
		return result;
	}
	
	public static HttpRequestBase getRequestModel(RequestMethod method,
			String url) {
		HttpRequestBase requestBase = null;
		if (method == RequestMethod.GET) {
			requestBase = new HttpGet(url);
		} else if (RequestMethod.POST == method) {
			requestBase = new HttpPost(url);
		}
		return requestBase;
	}
	
	// 得到验证参数
	private String getSignature(String accountsid, String authtoken,
			String timestamp, EncryptUtil encryptUtil) throws Exception {
		String sig = accountsid + authtoken + timestamp;
		String signature = encryptUtil.md5Digest(sig);
		return signature;
	}
	
	/**
	 * 
	 * @return
	 */
	public DefaultHttpClient getDefaultHttpClient() {
		DefaultHttpClient httpclient = null;
		try {
			SSLHttpClient chc = new SSLHttpClient();
			httpclient = chc.registerSSL(getConfig().getHost(), "TLS",
					getConfig().getPort(), "https");
			HttpParams hParams = new BasicHttpParams();
			hParams.setParameter("https.protocols", "SSLv3,SSLv2Hello");
			httpclient.setParams(hParams);
		} catch (KeyManagementException e) {
			logger.error("getDefaultHttpClient", e.fillInStackTrace());
		} catch (NoSuchAlgorithmException e) {
			logger.error("getDefaultHttpClient", e.fillInStackTrace());
		}
		return httpclient;
	}
	
	/**
	 * 
	 * @param url
	 * @param timestamp
	 *            当前时间戳
	 * @param encryptUtil
	 *            加密工具类
	 * @return
	 */
	String fillUrl(String url, String timestamp, EncryptUtil encryptUtil) {
		url = url.replace("{SoftVersion}", getConfig().getSoftVersion());
		url = url.replace("{accountSid}", getConfig().getAccountSid());
		try {
			String signature = getSignature(getConfig().getAccountSid(),
					getConfig().getAuthToken(), timestamp, encryptUtil);
			url = url + "?sig=" + signature;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
		
	}
	
	@Override
	public Result callBack(String mainMobile, String toMobile) {
		String callBackUrl = getConfig().getHost()
				+ getConfig().getCallbackUrl();
		// MD5加密
		EncryptUtil encryptUtil = new EncryptUtil();
		// 时间戳
		String timestamp = DateUtil.dateToStr(new Date(),
				DateUtil.DATE_TIME_NO_SLASH);
		// 填充url
		callBackUrl = fillUrl(callBackUrl, timestamp, encryptUtil);
		
		Callback callback = new Callback();
		callback.setAppId(getConfig().getAppId());
		callback.setFromClient(mainMobile);
		callback.setTo(toMobile);
		Gson gson = new Gson();
		String body = gson.toJson(callback);
		body = "{\"callback\":" + body + "}";
		String resultJson = doPostRequest("application/json", callBackUrl,
				body, getConfig().getAccountSid(), timestamp, encryptUtil);
		return parseJosn(resultJson, "电话回拨", getConfig().getRequestSuccess());
	}
	
	@Override
	public Result sendVoiceCode(String mobile, String code) {
		String voiceCodeUrl = getConfig().getHost()
				+ getConfig().getSendVoiceCodeUrl();
		// MD5加密
		EncryptUtil encryptUtil = new EncryptUtil();
		// 时间戳
		String timestamp = DateUtil.dateToStr(new Date(),
				DateUtil.DATE_TIME_NO_SLASH);
		// 填充url
		voiceCodeUrl = fillUrl(voiceCodeUrl, timestamp, encryptUtil);
		
		VoiceCode voiceCode = new VoiceCode();
		voiceCode.setAppId(getConfig().getAppId());
		voiceCode.setVerifyCode(code);
		voiceCode.setTo(mobile);
		// json 串行化短信模版对象
		Gson gson = new Gson();
		String body = gson.toJson(voiceCode);
		body = "{\"voiceCode\":" + body + "}";
		
		// 请求对方
		String resultJson = doPostRequest("application/json", voiceCodeUrl,
				body, getConfig().getAccountSid(), timestamp, encryptUtil);
		
		return parseJosn(resultJson, "语音验证码", getConfig().getRequestSuccess());
	}
	
	/**
	 * 
	 * @param json
	 *            json串
	 * @param type
	 *            请求名（语音验证码，短信验证码，电话回拨）
	 * @return
	 */
	public static Result parseJosn(String json, String type, String successCode) {
		try {
			String respCode = null;
			JSONObject jsonObject = JSONObject.fromObject(json);
			respCode = jsonObject.getJSONObject("resp").getString("respCode");
			logger.info("请求的返回码为：{}", respCode);
			if (successCode != null && !successCode.equals("")) {
				if (successCode.equals(respCode) && respCode != null) {
					return new Result(true, respCode, "发送" + type + "成功",
							"云之讯平台");
				}
			}
			return new Result(false, respCode, "发送" + type + "失败", "云之讯平台");
		} catch (JSONException e) {
			logger.error("parseJosnStr", e.fillInStackTrace());
			return new Result(false, "error:get respCode from json", "发送"
					+ type + "失败", "云之讯平台");
		}
	}
}
