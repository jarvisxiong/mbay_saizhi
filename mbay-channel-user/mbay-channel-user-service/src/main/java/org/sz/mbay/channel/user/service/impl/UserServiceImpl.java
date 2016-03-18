package org.sz.mbay.channel.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.base.exception.error.RegisterError;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.bean.UserContext;
import org.sz.mbay.channel.user.dao.UserDao;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.qo.CertificateQO;
import org.sz.mbay.channel.user.service.MBAccountService;
import org.sz.mbay.channel.user.service.RedTrafficAccountService;
import org.sz.mbay.channel.user.service.UserService;
import org.sz.mbay.channel.useraccount.bean.UserAccount;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.fs.FSClient;
import org.sz.mbay.fs.FSClientFactory;
import org.sz.mbay.fs.FSClientFactory.ClientType;

@Service("UserServiceImpl")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserDao userDao;
	
	@Autowired
	MBAccountService mbayTrafficService;
	
	@Autowired
	RedTrafficAccountService redTrafficService;

	private FSClient fsClient = FSClientFactory.getClient(ClientType.FDFS);

	@Override
	public User findChannel(String loginname) {
		return this.userDao.findChannel(loginname);
	}

	@Override
	public ChannelInfo findChannelShortInfo(int channelid) {
		try {
			return this.userDao.findChannleShortInfo(channelid);
		} catch (Exception e) {
			LOGGER.error("findChannelInfo", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public User findUser(String usernumber) {
		try {
			return this.userDao.getBean(usernumber, User.class);
		} catch (Exception e) {
			LOGGER.error("findUserById", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	@Transactional
	public User registerUser(User user) throws RuntimeException {
		// /user.setPrivatekey((System.currentTimeMillis() + "").substring(0,
		// 8));
		try {
			user.setSupnumber(user.getSupnumber());
			user.setCertStatus(CertStatus.UNAPPLY);
			user.setUsernumber(generateUserNumber(user.getUsername()));

			// md5加密密码
			String oldPassword = user.getPassword();
			String newPassword = DigestUtils.md5Encrypt(oldPassword);
			user.setPassword(newPassword);

			user = userDao.createBean(user);
			UserAccount account = new UserAccount();
			account.setCreditLimit(0);
			account.setAmount(new BigDecimal(0));
			account.setStatus(0);
			account.setUserNumber(user.getUsernumber());
			account = this.userDao.createBean(account);
			UserContext context = new UserContext();
			context.setSmscount(100);
			context.setEvent_inuse(0);
			context.setSubagent(0);
			context.setUsernumber(user.getUsernumber());
			this.userDao.createBean(context);
			// 创建美贝流量账户
			this.mbayTrafficService.createMbayTrafficAccount(user.getUsernumber());
			// 创建流量红包产品账户
			this.redTrafficService.createRedTrafficAccount(user.getUsernumber());
			return user;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("registerUser", e.fillInStackTrace());
			throw new ServiceException(RegisterError.REG_FAIL);
		}
	}

	@Override
	public String getPrivatekeyByUid(int uid) {
		return this.userDao.getPrivatekeyByUid(uid);
	}

	@Override
	public User findSubNumberAndAuthState(String usernumber) {
		return this.userDao.findSubNumberAndAuthState(usernumber);
	}

	@Override
	public boolean isExistUserName(String username) {
		return this.userDao.isExistUserName(username) == 1;

	}

	@Override
	public ExecuteResult authInvitationCode(String usernumber) {
		int flag = this.userDao.findUserCountByNumber(usernumber);
		if (flag == 1) {
			return new ExecuteResult(true, "验证信息通过！");
		}
		return new ExecuteResult(false, "邀请码不存在!");
	}

	/**
	 * 暂时先不检查用户编号是否已存在
	 * 
	 * @param source
	 * @return
	 */
	private String generateUserNumber(String source) {
		return DigestUtils.crc32(source);
	}

	@Override
	@Transactional
	// TODO 上级编号变更涉及代理数量变更应一并写往存储过程。
	public ExecuteResult changeSupNumber(String userNumber, String supNumber) {
		int count = this.userDao.findUserCountByNumber(supNumber);// 所修改为的上级编号信息
		if (count == 0) {
			return new ExecuteResult(false, "上级编号不存在");
		}
		try {
			// 增加
			this.userDao.increaseSubAgent(supNumber);
			// User user = this.userDao.getSupUserByID(ChannelContext
			// .getSessionChannel().getId());// 获取原上级
			// if (user != null) {// 减少原上级下级的代理数
			// this.userDao.reduceSubAgent(user.getUsernumber());
			// }
			this.userDao.changeUserSupNumber(userNumber, supNumber);

		} catch (Exception e) {
			LOGGER.error("上级编号变更异常", e.fillInStackTrace());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ExecuteResult(false, "上级编号变更失败");
		}
		return new ExecuteResult(true, "");
	}

	@Override
	public boolean updateUserPassword(String password, String userNumber) {
		return this.userDao.updateUserPassword(password, userNumber) == 1 ? true : false;
	}

	@Override
	public User findUser(int userid) throws Exception {
		return this.userDao.findUserByID(userid);
	}

	@Override
	public User findUserAuthAndPropertyByName(String username) {
		return this.userDao.findUserAuthAndPropertyByName(username);

	}

	@Override
	public int getIDByUserName(String name) {
		return this.userDao.getIDByUserName(name);
	}

	@Override
	public int getIDByUserNumber(String userNumber) {
		return this.userDao.getIDByUserNumber(userNumber);
	}

	@Override
	public boolean authUserPassword(String usernumber, String password) {
		String pwd = this.userDao.getUserPassword(usernumber);
		if (pwd.equals(password)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean authOriginalpwd(String usernumber, String password) {
		String pwd = this.userDao.getUserPassword(usernumber);
		if (pwd.equals(password)) {
			return false;
		}
		return true;
	}

	@Override
	public List<User> findSubAgents(String usernumber) {
		return this.userDao.findSubAgents(usernumber);
	}

	@Override
	public boolean authExistUserByUserNumber(String usernumber) {
		return this.userDao.findUserCountByNumber(usernumber) == 1;
	}

	@Override
	public ChannelProperty findUserProperty(String usernumber) {
		return this.userDao.findUserProperty(usernumber);
	}

	@Override
	public String findChannelCertRealName(String usernumber) {
		String certName = this.userDao.findChannelCertRealName(usernumber);
		return User.getRealNameWithEncrypt(certName) + "(" + usernumber + ")";

	}

	@Override
	public String findChannelCompanyName(String usernumber) {
		return this.userDao.findChannelCompanyName(usernumber);
	}

	@Override
	public CertStatus findUserCertStatus(String userNumber) {
		return this.userDao.findUserCertStatus(userNumber);
	}

	@Override
	public List<User> findAllChannel(User user, PageInfo pageinfo) {
		try {
			return this.userDao.findList(user, pageinfo, "User");
		} catch (Exception e) {
			LOGGER.error("findAllUser", e.fillInStackTrace());
		}
		return new ArrayList<User>();
	}

	@Override
	public List<User> finaAllAuthApply(CertificateQO certificateQO, PageInfo pageinfo) {
		try {
			return this.userDao.finaAllAuthApply(certificateQO, pageinfo);
		} catch (Exception e) {
			LOGGER.error("finaAllAuthApply", e.fillInStackTrace());
		}
		return new ArrayList<User>();
	}

	@Override
	public ChannelProperty findUserPropertyByUid(int userid) {
		return this.userDao.findUserPropertyByUid(userid);
	}

	@Override
	public CertStatus getCertificateAuthState(int userid) {
		return null;
	}

	@Override
	public User findUserByUserId(long userId) {
		return userDao.findUserByUserId(userId);
	}

	@Override
	public boolean updateChannel(ChannelInfo channelinfo) {
		return userDao.updateChannel(channelinfo);
	}

	@Override
	public List<Integer> findAllUserId() {
		return userDao.findAllUserId();
	}

	@Override
	public ExecuteResult uploadPortrait(String portrait, String userNumber) {
		String path = userDao.findPortrait(userNumber);
		if (!StringUtils.isEmpty(path)) {
			fsClient.deleteFile(path);
		}
		return this.userDao.uploadPortrait(portrait, userNumber) > 0 ? ExecuteResult.successExecute
				: new ExecuteResult(false, "上传头像失败");
	}

	@Override
	public String findPortrait(String userNumber) {
		return this.userDao.findPortrait(userNumber);
	}
}
