package org.sz.mbay.base.trove.strategy;

import gnu.trove.strategy.HashingStrategy;

/**
 * @Description: TCustomerMap String strategy
 * @author han.han
 * @date 2014-10-25 上午12:37:35
 * 
 */
public class StringHashStrategy implements HashingStrategy<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8389213866548152319L;
	
	@Override
	public int computeHashCode(String arg0) {
		return arg0.hashCode();
	}
	
	@Override
	public boolean equals(String arg0, String arg1) {
		if (arg0.equals(arg1)) {
			return true;
		}
		return false;
	}
	
}
