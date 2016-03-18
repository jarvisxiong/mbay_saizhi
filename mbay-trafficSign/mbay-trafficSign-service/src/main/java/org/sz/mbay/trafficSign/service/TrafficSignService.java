package org.sz.mbay.trafficSign.service;

import java.util.List;

import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.trafficSign.bean.TrafficSign;
import org.sz.mbay.trafficSign.enums.TrafficSignStatus;

public interface TrafficSignService {
	
	public void add(TrafficSign bean);
	
	// 根据用户编号查询记录
	public TrafficSign findTrafficSignByUserNumber(String usernumber);
	
	public void del(int id);
	
	// 查询 number->用户编号
	public List<TrafficSign> findAllTrafficSign(PageInfo pageinfo,
			String number);
			
	// 根据id查询记录
	public TrafficSign findTrafficSignById(int id);
	
	// 审核 reason->不通过原因,status->状态 1:审核通过,2:审核不通过
	public ExecuteResult audit(int id, String reason, TrafficSignStatus status);
	
	// 修改状态 id->id,enable->状态
	public void change(int id, EnableState enable);
}
