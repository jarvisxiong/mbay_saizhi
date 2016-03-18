package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.LiuMiRecord;

/**
 * 
 * @Description: 流米相关
 * @author frank.zong
 * @date 2015年2月13日 下午13:43:42
 * 
 */
public interface LiuMiService {
	
	public boolean updateTotalPrice(double price);
	
	public List<LiuMiRecord> findList(PageInfo pageinfo);
	
	public double findLiuMiTotalPrice();
}
