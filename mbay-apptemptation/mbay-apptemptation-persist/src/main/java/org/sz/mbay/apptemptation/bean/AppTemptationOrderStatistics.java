package org.sz.mbay.apptemptation.bean;

/**
 * app诱惑订单统计
 * 
 * @author jerry
 */
public class AppTemptationOrderStatistics {
	
	private int successNums;
	
	private int failNums;
	
	private int rechargingNums;
	
	private int unknownNums;
	
	public int getSuccessNums() {
		return successNums;
	}
	
	public void setSuccessNums(int successNums) {
		this.successNums = successNums;
	}
	
	public int getFailNums() {
		return failNums;
	}
	
	public void setFailNums(int failNums) {
		this.failNums = failNums;
	}
	
	public int getRechargingNums() {
		return rechargingNums;
	}
	
	public void setRechargingNums(int rechargingNums) {
		this.rechargingNums = rechargingNums;
	}
	
	public int getUnknownNums() {
		return unknownNums;
	}
	
	public void setUnknownNums(int unknownNums) {
		this.unknownNums = unknownNums;
	}
	
	public int getTotalNums() {
		return unknownNums + rechargingNums + failNums + successNums;
	}
}
