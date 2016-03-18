package org.sz.mbay.sms.service;

import org.sz.mbay.sms.bean.Result;

public interface YZXSMSService extends SMSService {
    public Result callBack(String mainMobile, String toMobile);

    public Result sendVoiceCode(String mobile, String code);
}
