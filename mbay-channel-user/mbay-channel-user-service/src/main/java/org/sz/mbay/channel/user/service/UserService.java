package org.sz.mbay.channel.user.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.qo.CertificateQO;

public interface UserService extends BaseService {
	
	/**
	 * 根据用户名返回用户信息下
	 * 
	 * @param loginname
	 * @return
	 */
	public User findChannel(String loginname);
	
	/**
	 * 修改用户密码
	 * 
	 * @param password
	 * @param userid
	 * @return
	 */
	public boolean updateUserPassword(String password, String userNumber);
	
	/**
	 * @param channelid
	 * @return
	 */
	public ChannelInfo findChannelShortInfo(int channelid);
	
	/**
	 * @param id
	 * @return
	 */
	public User findUser(String usernumber);
	
	/**
	 * @param id
	 * @return
	 */
	public User findUser(int userid) throws Exception;
	
	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	public User registerUser(User user);
	
	/**
	 * 获取用户密钥
	 * 
	 * @param uid
	 * @return
	 */
	public String getPrivatekeyByUid(int uid);
	
	/**
	 * 根据转账编号，此帐号是否可用于转账
	 * 
	 * @param usernumber
	 * @return
	 */
	public User findSubNumberAndAuthState(String usernumber);
	
	/**
	 * 根据用户姓名查询用户实名认证信息和渠道类型
	 * 
	 * @param username
	 * @return
	 */
	public User findUserAuthAndPropertyByName(String username);
	
	/**
	 * 根据用户编号查询用户实名状态
	 * 
	 * @param userNumber
	 * @return
	 */
	public CertStatus findUserCertStatus(String userNumber);
	
	public int getIDByUserName(String name);
	
	/**
	 * 根据用户编号返回用户Id
	 * 
	 * @param userNumber
	 * @return
	 */
	public int getIDByUserNumber(String userNumber);
	
	/**
	 * 判断登录帐户名是否存在
	 * @param userName 登录账户名
	 * @return 存在返回true，不存在返回false
	 */
	public boolean isExistUserName(String userName);
	
	/**
	 * 更改用户上级编号
	 * 
	 * @param supnumber
	 * @return
	 */
	public ExecuteResult changeSupNumber(String userNumber, String supnumber);
	
	/**
	 * @param usernumber
	 * @return 下级Map集合包含用户编号和名称
	 */
	
	boolean authUserPassword(String usernumber, String password);
	
	public boolean authOriginalpwd(String usernumber, String password);
	
	public List<User> findSubAgents(String usernumber);
	
	/**
	 * 根据用户编号验证用户是否存在
	 * 
	 * @param usernumber
	 * @return
	 */
	public boolean authExistUserByUserNumber(String usernumber);
	
	/**
	 * 根据用户编号获得用户所属渠道类别
	 * 
	 * @param usernumber
	 * @return
	 */
	public ChannelProperty findUserProperty(String usernumber);
	
	/**
	 * 验证用户邀请码
	 * 
	 * @param username
	 * @return
	 */
	ExecuteResult authInvitationCode(String usernumber);
	
	/**
	 * 
	 * @param usernumber
	 *            当前登录登录用户编号
	 * @param inputusernumber
	 *            用户输入编号
	 * @return
	 */
	public String findChannelCertRealName(String usernumber);
	
	/**
	 * 查询编号对应的用户名称
	 * 
	 * @param usernumber
	 * @return
	 */
	public String findChannelCompanyName(String usernumber);
	
	// onembay
	public List<User> findAllChannel(User user, PageInfo pageinfo);
	
	/**
	 * 实名认证请求集合
	 * 
	 * @param user
	 * @param pageinfo
	 * @return
	 */
	public List<User> finaAllAuthApply(CertificateQO certificateQO, PageInfo pageinfo);
	
	public CertStatus getCertificateAuthState(int userid);
	
	public ChannelProperty findUserPropertyByUid(int userid);
	
	public User findUserByUserId(long userId);
	
	/**
	 * 修改企业认证信息
	 * 
	 * @param channelinfo
	 *            企业认证信息
	 * @return true|false
	 */
	public boolean updateChannel(ChannelInfo channelinfo);
	
	/**
	 * 查询所有企业信息userid
	 */
	public List<Integer> findAllUserId();
	
	/**
	 * 上传头像
	 * @param portrait 头像图片路径
	 * @param userNumber
	 * @return
	 */
	public ExecuteResult uploadPortrait(String portrait, String userNumber);
	
	/**
	 * 查询用户编号对应的头像图片
	 * @param userNumber
	 * @return
	 */
	public String findPortrait(String userNumber);
}
