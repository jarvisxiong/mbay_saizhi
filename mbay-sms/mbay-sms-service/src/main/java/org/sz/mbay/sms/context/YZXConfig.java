package org.sz.mbay.sms.context;

public class YZXConfig {
    private String softVersion;
    private String accountSid;
    private String appId;
    private String templateId;
    private String param;
    private String authToken;
    private String host;
    private String sendSMSUrl;
    private String callbackUrl;
    private String sendVoiceCodeUrl;
    private String requestSuccess;
    private int port;

    public String getSoftVersion() {
	return softVersion;
    }

    public void setSoftVersion(String softVersion) {
	this.softVersion = softVersion;
    }

    public String getAccountSid() {
	return accountSid;
    }

    public void setAccountSid(String accountSid) {
	this.accountSid = accountSid;
    }

    public String getAppId() {
	return appId;
    }

    public void setAppId(String appId) {
	this.appId = appId;
    }

    public String getTemplateId() {
	return templateId;
    }

    public void setTemplateId(String templateId) {
	this.templateId = templateId;
    }

    public String getParam() {
	return param;
    }

    public void setParam(String param) {
	this.param = param;
    }

    public String getAuthToken() {
	return authToken;
    }

    public void setAuthToken(String authToken) {
	this.authToken = authToken;
    }

    public String getHost() {
	return host;
    }

    public void setHost(String host) {
	this.host = host;
    }

    public String getSendSMSUrl() {
	return sendSMSUrl;
    }

    public void setSendSMSUrl(String sendSMSUrl) {
	this.sendSMSUrl = sendSMSUrl;
    }

    public String getCallbackUrl() {
	return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
	this.callbackUrl = callbackUrl;
    }

    public String getSendVoiceCodeUrl() {
	return sendVoiceCodeUrl;
    }

    public void setSendVoiceCodeUrl(String sendVoiceCodeUrl) {
	this.sendVoiceCodeUrl = sendVoiceCodeUrl;
    }

    public String getRequestSuccess() {
	return requestSuccess;
    }

    public void setRequestSuccess(String requestSuccess) {
	this.requestSuccess = requestSuccess;
    }

    public int getPort() {
	return port;
    }

    public void setPort(String port) {
	this.port = Integer.valueOf(port);
    }
}
