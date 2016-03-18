package org.sz.mbay.channel.bean;
/**
 * 短信包表
 * @author meibei-hrain
 *
 */
public class SMSPackage {

    private int id;
    /*   美贝价格 */
    private double mbayAmount;
    /* 短信数量  */
    private int smsAmount;
    /* 创建时间  */
    private String createTime;
    /* 用户名称  */
    private String userName;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public double getMbayAmount() {
		return mbayAmount;
	}

	public void setMbayAmount(double mbayAmount) {
		this.mbayAmount = mbayAmount;
	}

	public int getSmsAmount() {
		return smsAmount;
	}

	public void setSmsAmount(int smsAmount) {
		this.smsAmount = smsAmount;
	}

    

}

