package org.sz.mbay.channel.notice.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.notice.bean.Notice;


/**
 * @author Tom
 * @version 创建时间：2014-9-1上午10:28:09
 * @type
 */
public interface NoticeService {
	
	List<Notice> findAllNotice(PageInfo pageinfo);
	
	Notice createNotice(Notice notice);
	
	ExecuteResult deleteNotice(int id);
	
	Notice findNoticeById(int id) throws Exception;
	
	int countNotice(Notice notice) throws Exception;
}
