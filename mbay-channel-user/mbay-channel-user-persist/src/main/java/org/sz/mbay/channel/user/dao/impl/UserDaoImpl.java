package org.sz.mbay.channel.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.dao.UserDao;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.qo.CertificateQO;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User>implements UserDao {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDaoImpl.class);
			
	@Override
	public User findChannel(String loginname) {
		User user = new User();
		user.setUsername(loginname);
		try {
			user = (User) this.template.selectOne("findChannelByUname", user);
		} catch (Exception e) {
			LOGGER.error("登录查询findChannel异常", e.fillInStackTrace());
			return null;
		}
		return user;
	}
	
	@Override
	public User findUserByID(int id) throws Exception {
		return super.template.selectOne("findUserByID", id);
	}
	
	@Override
	public ChannelInfo findChannleShortInfo(int id) {
		return super.template.selectOne("findChannelShortInfo", id);
	}
	
	@Override
	public String getPrivatekeyByUid(long userid) {
		return super.template.selectOne("findPrivatekeyByUid", userid);
	}
	
	@Override
	public User findSubNumberAndAuthState(String unumber) {
		return super.template.selectOne("findUserByUNumber", unumber);
	}
	
	@Override
	public int isExistUserName(String username) {
		return super.template.selectOne("authRegUserName", username);
	}
	
	@Override
	public int reduceSubAgent(String unumber) throws Exception {
		
		return super.template.update("reduceSubAgent", unumber);
	}
	
	@Override
	public int increaseSubAgent(String usernumber) throws Exception {
		return super.template.update("increaseSubAgent", usernumber);
	}
	
	@Override
	public int changeUserSupNumber(String userNumber, String number) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supnumber", number);
		map.put("userid", userNumber);
		return super.template.update("updateUserSupnumber", map);
	}
	
	@Override
	public User getSupUserByID(int userid) {
		return super.template.selectOne("getSupUserByID", userid);
	}
	
	@Override
	public int updateUserPassword(String password, String userNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("passwd", password);
		map.put("userNumber", userNumber);
		return super.template.update("updateUserPassword", map);
	}
	
	@Override
	public List<Map<String, String>> findAllAgents(String usernumber) {
		return super.template.selectList("findAllAgents", usernumber);
	}
	
	@Override
	public User findUserAuthAndPropertyByName(String username) {
		return this.template.selectOne("findUserAuthAndPropertyByName",
				username);
				
	}
	
	@Override
	public int getIDByUserName(String name) {
		Integer inte = this.template.selectOne("getIDByUserName", name);
		if (inte == null) {
			return 0;
		}
		return inte;
	}
	
	@Override
	public String getUserPassword(String usernumber) {
		return this.template.selectOne("getUserPassword", usernumber);
	}
	
	@Override
	public List<User> findSubAgents(String usernumber) {
		return this.template.selectList("findSubAgents", usernumber);
	}
	
	@Override
	public List<User> findList() {
		List<User> list = null;
		try {
			list = this.template.selectList("findAllChannel");
		} catch (Exception e) {
			LOGGER.error("ChannelDao findList Error", e.fillInStackTrace());
		}
		return list;
	}
	
	@Override
	public int findUserCountByNumber(String usernumber) {
		return this.template.selectOne("findUserCountByNumber", usernumber);
	}
	
	@Override
	public ChannelProperty findUserProperty(String usernumber) {
		User user = this.template.selectOne("findUserPropertyByUserNumber",
				usernumber);
		return user.getProperty();
	}
	
	@Override
	public String findChannelCertRealName(String usernumber) {
		return this.template.selectOne("findChannelCertRealName", usernumber);
	}
	
	@Override
	public String findChannelCompanyName(String usernumber) {
		return this.template.selectOne("findChannelCompanyName", usernumber);
	}
	
	@Override
	public CertStatus findUserCertStatus(String userNumber) {
		return this.template.selectOne("findUserCertStatus", userNumber);
	}
	
	@Override
	public int getIDByUserNumber(String userNumber) {
		return this.template.selectOne("getIDByUserNumber", userNumber);
	}
	
	@Override
	public List<User> finaAllAuthApply(CertificateQO certificateQO,
			PageInfo pageinfo) {
		try {
			return super.findList(certificateQO, pageinfo, "AuthApply");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ChannelProperty findUserPropertyByUid(int userid) {
		return ((User) this.template.selectOne("findUserPropertyByUid", userid))
				.getProperty();
	}
	
	@Override
	public User findUserByUserId(long userId) {
		return this.template.selectOne("findUserByUserId", userId);
	}
	
	@Override
	public void updateUserByUserId(int userId, String certState) {
	
	}
	
	@Override
	public boolean updateChannel(ChannelInfo channelinfo) {
		try {
			return super.updateBean(channelinfo) == 1;
		} catch (Exception e) {
			LOGGER.error("ChannelDao updateChannel Error",
					e.fillInStackTrace());
		}
		return false;
	}
	
	@Override
	public List<Integer> findAllUserId() {
		return super.template.selectList("findAllUserId");
	}
	
	@Override
	public int uploadPortrait(String portrait, String userNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("portrait", portrait);
		map.put("userNumber", userNumber);
		return super.template.update("uploadPortrait", map);
	}
	
	@Override
	public String findPortrait(String userNumber) {
		return this.template.selectOne("findPortraitByUserNumber", userNumber);
	}
}
