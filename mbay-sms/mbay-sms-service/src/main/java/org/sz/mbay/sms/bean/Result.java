package org.sz.mbay.sms.bean;

public class Result {
    private boolean success;
    private String code;
    private String desc;
    private String serivcename;

    public Result(boolean success, String code, String desc, String serivcename) {
	super();
	this.success = success;
	this.code = code;
	this.desc = desc;
	this.serivcename = serivcename;
    }

    public boolean isSuccess() {
	return success;
    }

    public void setSuccess(boolean success) {
	this.success = success;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public String getSerivcename() {
	return serivcename;
    }

    public void setSerivcename(String serivcename) {
	this.serivcename = serivcename;
    }

}
