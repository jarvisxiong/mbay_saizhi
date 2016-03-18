package org.sz.mbay.channel.dao;
import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.Notice;



/**
 *@author Tom
 *@version 创建时间：2014-9-1上午10:25:05
 *@type 类型描述  
 */
public interface NoticeDao extends BaseDao<Notice> {

	int updateNoticeflag(int id);
	
	
	
	
	
	

}
