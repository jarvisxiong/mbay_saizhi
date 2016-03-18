package org.sz.mbay.channel.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.channel.bean.AssetBank;
import org.sz.mbay.channel.dao.AssetBankDao;
import org.sz.mbay.channel.service.AssetBankService;

@Service
public class AssetBankServiceImpl implements AssetBankService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AssetBankServiceImpl.class);

    @Autowired
    AssetBankDao dao;

    @Override
    public List<AssetBank> findList() {
    	try {
            return this.dao.findList();
        } catch (Exception e) {
            LOGGER.error("AssetBankService findList Error",e.fillInStackTrace());
        }
        return new ArrayList<AssetBank>();
    }
    
    @Override
    public AssetBank findAssetBankById(int id) {
    	AssetBank bean = null;
        try{
            bean = this.dao.findAssetBankById(id);
        }catch(Exception e){
            LOGGER.error("AssetBankService findAssetBankById Error",e.fillInStackTrace());
        }
        return bean;
    }
}