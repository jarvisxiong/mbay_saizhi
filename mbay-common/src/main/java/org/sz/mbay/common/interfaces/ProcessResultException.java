package org.sz.mbay.common.interfaces;

/**
 * 处理接口返回值异常
 * 
 * @author jerry
 */
public class ProcessResultException extends Exception {
	
	private static final long serialVersionUID = 2547013886133203047L;
	
	public ProcessResultException(String message) {
		super(message);
	}
	
}
