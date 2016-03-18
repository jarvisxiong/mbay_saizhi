package org.sz.mbay.duiba.bean;

import java.io.Serializable;

/**
 * 兑吧活动对应关系实体bean
 * @author Frank
 *
 */
@SuppressWarnings("serial")
public class DuiBaRelationShip implements Serializable {

	private String usernumber; //usernumber
	private DuiBaMall mall; //商城
    
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public DuiBaMall getMall() {
		return mall;
	}
	public void setMall(DuiBaMall mall) {
		this.mall = mall;
	}
}