package org.sz.mbay.channel.notice.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.notice.bean.Notice;
import org.sz.mbay.channel.notice.dao.NoticeDao;

/**
 * @author Tom
 * @version 创建时间：2014-9-1上午10:27:09
 * @type 类型描述
 */

@Repository
public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements NoticeDao {
	
	@Override
	public int updateNoticeflag(int id) {
		
		return super.template.update("updateNoticeflag", id);
		
	}
	
}
