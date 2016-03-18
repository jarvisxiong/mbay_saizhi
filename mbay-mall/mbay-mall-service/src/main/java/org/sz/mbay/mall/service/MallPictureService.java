package org.sz.mbay.mall.service;

import java.util.List;

import org.sz.mbay.mall.bean.MallPicture;
import org.sz.mbay.mall.enums.MallPictureType;

public interface MallPictureService {
	
	/**
	 * 删除
	 * 
	 * @param itemNumber
	 * @return
	 */
	public boolean del(String itemNumber);
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean del(int id);
	
	/**
	 * 新增
	 * 
	 * @param bean
	 */
	public void add(MallPicture bean);
	
	/**
	 * 更新
	 * 
	 * @param bean
	 */
	public void update(MallPicture bean);
	
	/**
	 * 查询(除了详情图之外)
	 * 
	 * @param itemNumber
	 * @param type
	 * @return
	 */
	public MallPicture findOne(String itemNumber, MallPictureType type);
	
	/**
	 * 根据兑换码编号查询
	 * 
	 * @param itemNumber
	 * @return
	 */
	public List<MallPicture> findList(String itemNumber);
	
	/**
	 * 根据兑换码编号查询详情图
	 * @param itemNumber
	 * @return
	 */
	public List<MallPicture> findDetails(String itemNumber);
	
}
