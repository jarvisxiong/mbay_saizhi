package org.sz.mbay.apptemptation.service;

import java.util.List;

import org.sz.mbay.apptemptation.bean.AppTemptationIpWhiteList;
import org.sz.mbay.apptemptation.qo.AppTemptationIpWhiteListQO;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.BaseService;

public interface AppTemptationIpWhiteListService extends BaseService {
	
	/**
	 * 查找实体
	 * 
	 * @param usernumber
	 * @return
	 */
	AppTemptationIpWhiteList findByUserNumber(String usernumber);
	
	/**
	 * 分页查询
	 * 
	 * @param qo
	 * @param pageInfo
	 * @return
	 */
	List<AppTemptationIpWhiteList> findPage(AppTemptationIpWhiteListQO qo,
			PageInfo pageInfo);
			
	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	boolean updateByIdSelective(AppTemptationIpWhiteList bean);
	
	/**
	 * 新增
	 * 
	 * @param bean
	 * @return
	 */
	boolean create(AppTemptationIpWhiteList bean);
	
	/**
	 * 检测记录是否存在
	 * 
	 * @param userNumber
	 * @return
	 */
	boolean checkExistsByUserNumber(String userNumber);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteById(Long id);
	
	/**
	 * 查找记录
	 * 
	 * @param id
	 * @return
	 */
	AppTemptationIpWhiteList findById(Long id);
	
}
