package org.sz.mbay.operator.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @author Tom
 * @version 创建时间：2014-9-24下午2:35:41 合同对象
 */
@SuppressWarnings("serial")
public class Contract implements Serializable {

    /** 标示id */
    private int id;
    /** 合同id */
    private String contractid;

    /**
     * 合同（图片形式）副本
     */
    private String contractCopy;

    /** 合同起始时间 ***/
    private DateTime starttime;

    /** 合同结束时间 ***/
    private DateTime endtime;

    private int operatorid;

    public String getContractCopy() {
	return contractCopy;
    }

    public void setContractCopy(String contractCopy) {
	this.contractCopy = contractCopy;
    }

    public DateTime getStarttime() {
	return starttime;
    }

    public void setStarttime(DateTime starttime) {
	this.starttime = starttime;
    }

    public DateTime getEndtime() {
	return endtime;
    }

    public void setEndtime(DateTime endtime) {
	this.endtime = endtime;
    }

    public String getContractid() {
	return contractid;
    }

    public void setContractid(String contractid) {
	this.contractid = contractid;
    }

    public int getOperatorid() {
	return operatorid;
    }

    public void setOperatorid(int operatorid) {
	this.operatorid = operatorid;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

}
