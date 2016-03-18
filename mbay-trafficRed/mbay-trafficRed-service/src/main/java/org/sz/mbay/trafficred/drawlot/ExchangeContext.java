package org.sz.mbay.trafficred.drawlot;

/**
 * 上下文
 * 
 * @author jerry
 */
public class ExchangeContext {
	
	// 兑换策略
	protected IExchange exchange;
	
	// 单例
	private static ExchangeContext instance;
	
	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static ExchangeContext getInstance() {
		if (instance == null) {
			synchronized (ExchangeContext.class) {
				if (instance == null) {
					instance = new ExchangeContext();
				}
			}
		}
		return instance;
	}
	
	public IExchange getExchange() {
		return exchange;
	}
	
	public void setExchange(IExchange exchange) {
		this.exchange = exchange;
	}
	
	/**
	 * 兑换
	 * 
	 * @param param
	 * @return
	 */
	public Response exchange(IParam param) {
		return exchange.exchange(param);
	}
}
