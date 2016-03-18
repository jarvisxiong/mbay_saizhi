package org.sz.mbay.statistics.event;

/**
 * url进入登记
 * 
 * @author jerry
 */
public class UrlEnter extends BaseEvent {
	
	public UrlEnter() {
		super("001");
	}
	
	@Override
	public String toString() {
		return "访问url【" + getUrl() + "】";
	}
	
}
