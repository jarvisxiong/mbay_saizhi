package org.sz.mbay.channel.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.user.dao.UserAccountDao;
import org.sz.mbay.channel.useraccount.bean.UserAccount;

@Repository
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount>
		implements UserAccountDao {
		
	@Override
	public boolean reduceAccountMbay(String userNumber, double mbay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("mbay", mbay);
		return super.template.update("reduceAccountMbay", map) == 1;
		
	}
	
	@Override
	public int increaseAccountMbay(String userNumber, double mbay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("mbay", mbay);
		return super.template.update("increaseAccountMbay", map);
		
	}
	
	@Override
	public UserAccount findUserAccount(String userNumber) {
		return super.template.selectOne("findUserAccount", userNumber);
	}
	
	@Override
	public UserAccount findUserAccountWithLocked(String userNumber) {
		return super.template.selectOne("findUserAccountWithLocked",
				userNumber);
	}
	
	@Override
	public int increaseLockedMbay(String userNumber, double mbay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("mbay", mbay);
		return this.template.update("increaseLockedMbay", map);
	}
	
	@Override
	public boolean reduceLockedMbay(String userNumber, double mbay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNumber", userNumber);
		map.put("mbay", mbay);
		return this.template.update("reduceLockedMbay", map) == 1;
	}
	
}
