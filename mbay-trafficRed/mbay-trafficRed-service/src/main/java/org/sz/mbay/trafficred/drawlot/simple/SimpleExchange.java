package org.sz.mbay.trafficred.drawlot.simple;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.math.RandomUtils;
import org.sz.mbay.trafficred.drawlot.IExchange;
import org.sz.mbay.trafficred.drawlot.IParam;
import org.sz.mbay.trafficred.drawlot.Response;

/**
 * 简单抽奖
 * 
 * @author jerry
 */
public class SimpleExchange implements IExchange {
	
	public Response exchange(IParam param) {
		if (param == null || !(param instanceof SimpleParam)) {
			return SimpleResponse.PARAM_ERROR;
		}
		
		SimpleParam pm = (SimpleParam) param;
		
		if (pm.getRatio() == null) {
			return SimpleResponse.RATIO_ERROR;
		}
		
		BigDecimal dec = new BigDecimal(pm.getRatio());
		double rt = dec.setScale(5, RoundingMode.HALF_UP).doubleValue();
		
		if (rt < 0 || rt > 100) {
			return SimpleResponse.RATIO_ERROR;
		}
		if (rt == 0) {
			return SimpleResponse.NOT_HIT;
		}
		
		int[] fac = getProperFraction(rt);
		for (int i = 0; i < fac[1]; i++) {
			if (RandomUtils.nextInt(fac[0]) == 0) {
				return SimpleResponse.HIT;
			}
		}
		
		return SimpleResponse.NOT_HIT;
	}
	
	/*
	 * 获取概率的最简真分数
	 */
	public static int[] getProperFraction(double rate) {
		int m = 10000000, n = (int) (rate * 100000);
		for (int i = n; i > 1; i--) {
			if (m % i == 0 && n % i == 0) {
				m /= i;
				n /= i;
				i = n;
			}
		}
		return new int[] { m, n };
	}
}
