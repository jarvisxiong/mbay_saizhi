package org.sz.mbay.sms.service.impl;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.sms.bean.Result;
import org.sz.mbay.sms.enums.SmsServiceType;
import org.sz.mbay.sms.sdk.WebServiceStub;
import org.sz.mbay.sms.service.SDKSMSService;
import org.sz.mbay.sms.util.SDKSMSUtil;
import org.sz.mbay.sms.util.SmsUtil;

public class SDKSMSServiceImpl implements SDKSMSService {
    private Logger logger = LoggerFactory.getLogger(SDKSMSServiceImpl.class);

    private final String sn = "SDK-ONE-010-03015";
    private String pwd = "755097";

    public SDKSMSServiceImpl() {
	this.pwd = DigestUtils.md5Encrypt(this.sn + this.pwd).toUpperCase();
    }

    public String checkbalance() {
	String result = null;
	try {
	    WebServiceStub sdkstub = new WebServiceStub();
	    WebServiceStub.Balance balance = new WebServiceStub.Balance();
	    balance.setSn(this.sn);
	    balance.setPwd(this.pwd);
	    result = sdkstub.balance(balance).getBalanceResult();
	    return result;
	} catch (AxisFault e) {
	    this.logger.error("SDKSMSServiceImpl->accountBalance 异常",
		    e.fillInStackTrace());
	    e.printStackTrace();
	} catch (RemoteException e) {
	    this.logger.error("SDKSMSServiceImpl->accountBalance 异常",
		    e.fillInStackTrace());
	    e.printStackTrace();
	}
	return result;
    }

    public String recharge(String cardnumber, String password) {
	try {
	    WebServiceStub sdkstub = new WebServiceStub();
	    WebServiceStub.ChargUp charg = new WebServiceStub.ChargUp();
	    charg.setSn(this.sn);
	    charg.setPwd(this.pwd);
	    charg.setCardno(cardnumber);
	    charg.setPwd(password);
	    return sdkstub.chargUp(charg).getChargUpResult();
	} catch (AxisFault e) {
	    this.logger
		    .error("SDKSMSService->chargUp 异常", e.fillInStackTrace());
	    e.printStackTrace();
	} catch (RemoteException e) {
	    this.logger
		    .error("SDKSMSService->chargUp 异常", e.fillInStackTrace());
	    e.printStackTrace();
	}
	return "";
    }

    @Override
    public Result sendSMS(String mobiles, int smsId, String param) {
	if (logger.isInfoEnabled()) {
	    logger.info("SDK短信接口执行短信发送.号码：{}", mobiles);
	}

	try {
	    String content = SmsUtil.getSMSContent(smsId, param,
		    SmsServiceType.SDK);
	    if (logger.isInfoEnabled()) {
		logger.info("短信发送内容：{}", content);
	    }

	    WebServiceStub sdkstub = new WebServiceStub();
	    WebServiceStub.Mt mt = new WebServiceStub.Mt();
	    mt.setSn(this.sn);
	    mt.setPwd(this.pwd);
	    mt.setMobile(mobiles);
	    mt.setContent(content);
	    mt.setExt("");
	    mt.setStime("");
	    mt.setRrid("");
	    String smt = sdkstub.mt(mt).getMtResult();
	    // smt 返回标示id
	    this.logger.error("SDK mt Response:" + smt);
	    int code = 0;
	    try {
		// 转换异常
		if (smt.length() < 3) {
		    code = Integer.valueOf(smt).intValue();
		}
	    } catch (Exception localException) {
		localException.printStackTrace();
	    }

	    return new Result(code >= 0, code + "", code >= 0 ? "发送成功"
		    : SDKSMSUtil.getErrorDescByCode(code), "SDK服务平台");
	} catch (AxisFault e) {
	    this.logger.error("SDKSMSService->sendBatchSMS 异常",
		    e.fillInStackTrace());
	    e.printStackTrace();
	} catch (RemoteException e) {
	    this.logger.error("SDKSMSService->sendBatchSMS 异常",
		    e.fillInStackTrace());
	    e.printStackTrace();
	}
	return new Result(false, 0 + "", "发送失败", "SDK服务平台");
    }
}
