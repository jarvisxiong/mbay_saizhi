package org.sz.mbay.trafficred.drawlot.simple;

import org.sz.mbay.trafficred.drawlot.IParam;

public class SimpleParam implements IParam {
	
	// 中奖几率
	private Double ratio;
	
	public Double getRatio() {
		return ratio;
	}
	
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	
}
