package org.sz.mbay.mall.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.mall.bean.MallPicture;
import org.sz.mbay.mall.enums.MallPictureType;

public interface MallPictureDao extends BaseDao<MallPicture> {
	
	/**
	 * 查询-单条
	 * @param itemNumber
	 * @param type
	 * @return
	 */
	public MallPicture findOne(String itemNumber, MallPictureType type);
	
	/**
	 * 根据兑换码编号查询所有图片
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
	
	/**
	 * 根据兑换码编号删除
	 * @param itemNumber
	 * @return
	 */
	public boolean deleteMallPictureByItemNumber(String itemNumber);
	
}
