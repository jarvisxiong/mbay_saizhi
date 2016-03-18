package org.sz.mbay.channel.notice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.notice.bean.Notice;
import org.sz.mbay.channel.notice.dao.NoticeDao;
import org.sz.mbay.channel.notice.service.NoticeService;


/**
 *@author Tom
 *@version 创建时间：2014-9-1上午10:28:35
 *@type 类型描述  
 */

@Service
public class NoticeServiceImpl implements BaseService, NoticeService {

	
	@Autowired
	NoticeDao noticeDao;
	
	@Override
	public List<Notice> findAllNotice(PageInfo pageinfo) {
		
		try {
			return this.noticeDao.findList("Notice", pageinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Notice createNotice(Notice notice) {		
		try {
			return noticeDao.createBean(notice);
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public ExecuteResult deleteNotice(int id) {
			int ret= this.noticeDao.updateNoticeflag(id);
			if(ret!=1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return  new ExecuteResult(false, "删除失败");
			}			
			return new ExecuteResult(true, "");
	}

	@Override
	public Notice findNoticeById(int id) throws Exception {
		Notice notice= this.noticeDao.getBean(id);
		if(notice!=null){
			return notice;
		}
		
		return null;
	}
	@Override
	public int countNotice(Notice notice) throws Exception {
			return this.noticeDao.countBean(notice );
		
	}
	

}
