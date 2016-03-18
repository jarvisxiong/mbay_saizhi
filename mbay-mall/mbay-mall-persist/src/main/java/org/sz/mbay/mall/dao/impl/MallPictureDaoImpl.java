package org.sz.mbay.mall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.mall.bean.MallPicture;
import org.sz.mbay.mall.dao.MallPictureDao;
import org.sz.mbay.mall.enums.MallPictureType;

@Repository
public class MallPictureDaoImpl extends BaseDaoImpl<MallPicture> implements MallPictureDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallPictureDaoImpl.class);
	
	@Override
	public MallPicture findOne(String itemNumber, MallPictureType type) {
		MallPicture bean = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNumber", itemNumber);
		map.put("type", type);
		try {
			bean = this.template.selectOne("findAllMallPicture", map);
		} catch (Exception e) {
			LOGGER.error("MallPictureDao findOne Error", e.fillInStackTrace());
		}
		return bean;
	}
	
	@Override
	public List<MallPicture> findList(String itemNumber) {
		List<MallPicture> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNumber", itemNumber);
		try {
			list = super.findList(map, null, "MallPicture");
		} catch (Exception e) {
			LOGGER.error("MallPictureDao findList Error", e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public List<MallPicture> findDetails(String itemNumber) {
		List<MallPicture> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemNumber", itemNumber);
		map.put("type", MallPictureType.DETAIL);
		try {
			list = super.findList(map, null, "MallPicture");
		} catch (Exception e) {
			LOGGER.error("MallPictureDao findDetails Error", e.fillInStackTrace());
		}
		return list;
	}

	@Override
	public boolean deleteMallPictureByItemNumber(String itemNumber) {
		try {
			return template.delete("deleteMallPictureByItemNumber", itemNumber) > 0;
		} catch (Exception e) {
			LOGGER.error("MallPictureDao deleteMallPictureByItemNumber Error", e.fillInStackTrace());
		}
		return false;
	}
	
}
