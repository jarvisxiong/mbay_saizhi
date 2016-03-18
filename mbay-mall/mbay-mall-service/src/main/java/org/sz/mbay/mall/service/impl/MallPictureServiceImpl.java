package org.sz.mbay.mall.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;
import org.sz.mbay.mall.bean.MallPicture;
import org.sz.mbay.mall.dao.MallPictureDao;
import org.sz.mbay.mall.enums.MallPictureType;
import org.sz.mbay.mall.service.MallPictureService;

@Service
public class MallPictureServiceImpl implements MallPictureService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MallPictureServiceImpl.class);
	
	private FSClient client = FSClientFactory.getClient(ClientType.FDFS);
	
	@Autowired
	MallPictureDao dao;
	
	@Override
	public List<MallPicture> findList(String itemNumber) {
		return this.dao.findList(itemNumber);
	}
	
	@Override
	public List<MallPicture> findDetails(String itemNumber) {
		return this.dao.findDetails(itemNumber);
	}
	
	@Override
	public boolean del(String itemNumber) {
		List<MallPicture> list = findList(itemNumber);
		if(list != null && list.size() > 0){
			for(MallPicture picture : list){
				client.deleteFile(picture.getPicture());
			}
		}
		return this.dao.deleteMallPictureByItemNumber(itemNumber);
	}
	
	@Override
	public boolean del(int id){
		try {
			return this.dao.deleteBean(id) > 0;
		} catch (Exception e) {
			LOGGER.error("MallPictureService del Error", e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public void add(MallPicture bean) {
		try {
			this.dao.createBean(bean);
		} catch (Exception e) {
			LOGGER.error("MallPictureService add Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public void update(MallPicture bean) {
		try {
			this.dao.updateBean(bean);
		} catch (Exception e) {
			LOGGER.error("MallPictureService update Error", e.fillInStackTrace());
		}
	}
	
	@Override
	public MallPicture findOne(String itemNumber, MallPictureType type) {
		return this.dao.findOne(itemNumber, type);
	}
	
}
