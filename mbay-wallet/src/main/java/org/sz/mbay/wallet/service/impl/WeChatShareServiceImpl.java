package org.sz.mbay.wallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.wallet.bean.WeChatShare;
import org.sz.mbay.wallet.dao.WeChatShareDao;
import org.sz.mbay.wallet.service.WeChatShareService;

@Service
public class WeChatShareServiceImpl extends BaseServiceImpl implements WeChatShareService {
	
	@Autowired
	WeChatShareDao dao;

	@Override
	public List<WeChatShare> findAllWeChatShare() {
		return dao.findAllWeChatShare();
	}

}