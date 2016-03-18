package org.sz.mbay.channel.exception;

/** 
* @Description: 美贝账户交易异常
* @author han.han 
* @date 2014-11-27 上午12:51:15 
*  
*/
public class DealFailureException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5666244446822479938L;
	
	public DealFailureException(String message){
		super(message);
	}

}
