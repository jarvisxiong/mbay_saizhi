package org.sz.mbay.channel.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.bean.RemittanceRecord;

public interface RemittanceRecordService {
	
	// 新增
	void add(RemittanceRecord bean);
	
	// 查询-单条
	public RemittanceRecord findRemittanceRecordById(int id);
	
	// 查询-分页
	public List<RemittanceRecord> findList(RemittanceRecord bean, PageInfo pageinfo);
}
