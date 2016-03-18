package org.sz.mbay.wallet.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.wallet.bean.WeChatShare;
import org.sz.mbay.wallet.dao.WeChatShareDao;

@Repository
public class WeChatShareDaoImpl extends BaseDaoImpl<WeChatShare> implements WeChatShareDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeChatShareDaoImpl.class);
	
	@Override
	public List<WeChatShare> findAllWeChatShare() {
		List<WeChatShare> list = null;
		try {
			list = this.template.selectList("findAllWeChatShare");
		} catch (Exception e) {
			LOGGER.error("WeChatShareDaoImpl findAllWeChatShare Error", e.fillInStackTrace());
		}
		return list;
	}
}