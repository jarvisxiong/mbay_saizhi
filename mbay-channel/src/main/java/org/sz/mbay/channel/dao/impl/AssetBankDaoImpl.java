package org.sz.mbay.channel.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.AssetBank;
import org.sz.mbay.channel.dao.AssetBankDao;

@Repository
public class AssetBankDaoImpl extends BaseDaoImpl<AssetBank> implements AssetBankDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AssetBankDaoImpl.class);
    
    @Override
    public AssetBank findAssetBankById(int id) {
    	AssetBank bean = null;
        try{
        	bean = this.template.selectOne("findAssetBankById",id);
        }catch(Exception e){
            LOGGER.error("AssetBankDao findAssetBankById Error",e.fillInStackTrace());
        }
        return bean;
    }

	@Override
	public List<AssetBank> findList() {
		List<AssetBank> list = null;
        try{
        	list = this.template.selectList("findAllAssetBank");
        }catch(Exception e){
            LOGGER.error("AssetBankDao findList Error",e.fillInStackTrace());
        }
		return list;
	}
}