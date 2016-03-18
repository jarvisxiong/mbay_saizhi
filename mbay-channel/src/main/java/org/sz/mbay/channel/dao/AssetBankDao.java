package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.AssetBank;

public interface AssetBankDao extends BaseDao<AssetBank> {
		
    //查询-单条
    public AssetBank findAssetBankById(int id);
    
    //查询-所有
    public List<AssetBank> findList();
}