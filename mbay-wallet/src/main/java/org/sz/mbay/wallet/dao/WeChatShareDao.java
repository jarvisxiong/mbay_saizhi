package org.sz.mbay.wallet.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.wallet.bean.WeChatShare;

public interface WeChatShareDao extends BaseDao<WeChatShare> {
	
	public List<WeChatShare> findAllWeChatShare();
	
}
