package org.sz.mbay.sms.service;

import org.sz.mbay.sms.bean.Result;

public interface SMSService {
    /**
     * 发送短信（也可以发送多条，详情请看参数介绍）
     * 
     * @param mobiles
     *            发送的手机号 多个用','隔开,云之讯平台服务一次不能超过100个手机号
     * @param smsTemplateId
     *            短信模版id
     * @param param
     *            短信模版中占位符参数，多个用','隔开
     * @return
     */
    Result sendSMS(String mobiles, int smsId, String param);

    // 群发短信
    // Result SendBatchSMS(String mobiles, String content);

    /*
     * // 查询余额 String checkbalance();
     */

    /*
     * // 账户充值 String recharge(String cardnumber, String password);
     */
}
