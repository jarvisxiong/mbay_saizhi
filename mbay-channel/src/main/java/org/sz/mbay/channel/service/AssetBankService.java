package org.sz.mbay.channel.service;

import java.util.List;
import org.sz.mbay.channel.bean.AssetBank;

public interface AssetBankService {
	
    //查询-单条
    public AssetBank findAssetBankById(int id);
    
    //查询-分页
    public List<AssetBank> findList();
}