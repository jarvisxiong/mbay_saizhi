package org.sz.mbay.channel.user.dao;

import java.util.List;
import java.util.Map;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.bean.User;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.user.qo.CertificateQO;

public interface UserDao extends BaseDao<User> {
	
	/**
	 * 根据登录名查询登录用户
	 * 
	 * @param loginname
	 * @return
	 */
	public User findChannel(String loginname);
	
	/**
	 * 重置用户密码
	 * 
	 * @param password
	 * @param userid
	 * @author tom
	 * @time 2014-7-30下午3:11:01
	 */
	public int updateUserPassword(String password, String userNumber);
	
	/**
	 * 根据ID查询用户信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User findUserByID(int id) throws Exception;
	
	/**
	 * 根据ID查询渠道商简单信息。见包含公司名称以及微信ID
	 * 
	 * @param id
	 * @return
	 */
	public ChannelInfo findChannleShortInfo(int id);
	
	/**
	 * 根据用户id查询用户密钥
	 * 
	 * @param userid
	 * @return
	 */
	public String getPrivatekeyByUid(long userid);
	
	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param unumber
	 * @return
	 */
	public User findSubNumberAndAuthState(String unumber);
	
	/**
	 * 根据用户名查询用户是否存在
	 * 
	 * @param username
	 * @return
	 */
	public int isExistUserName(String username);
	
	/**
	 * 减少代理下级账户个数
	 * 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public int reduceSubAgent(String usernumber) throws Exception;
	
	/**
	 * 增加账户下级代理个数
	 * 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public int increaseSubAgent(String unumber) throws Exception;
	
	/**
	 * 变更用户上级编号
	 * 
	 * @param userid
	 * @param number
	 * @return
	 */
	public int changeUserSupNumber(String userNumber, String superNumber);
	
	/**
	 * 获取上级代理信息
	 * 
	 * @param userid
	 * @return
	 */
	public User getSupUserByID(int userid);
	
	/***
	 * 获取下级代理
	 * 
	 * @param usernumber
	 * @return
	 */
	public List<Map<String, String>> findAllAgents(String usernumber);
	
	/**
	 * 根据名字查询账户认证信息和渠道类别
	 * 
	 * @param username
	 * @return
	 */
	public User findUserAuthAndPropertyByName(String username);
	
	public int getIDByUserName(String name);
	
	/**
	 * 根据用户编号返回用户ID
	 * 
	 * @param name
	 * @return
	 */
	public int getIDByUserNumber(String userNumber);
	
	public String getUserPassword(String usernumber);
	
	public List<User> findSubAgents(String usernumber);
	
	// 获取所有记录
	public List<User> findList();
	
	/**
	 * 根据用户编号返回用户数量，用于验证用户是否存在，最大应只返回1
	 * 
	 * @param usernumber
	 *            用户编号
	 * @return 用户数量，最大为1
	 */
	public int findUserCountByNumber(String usernumber);
	
	/**
	 * 根据用户编号获得用户所属渠道类别
	 * 
	 * @param usernumber
	 * @return
	 */
	public ChannelProperty findUserProperty(String usernumber);
	
	/**
	 * 查询用户实名深证状态
	 * 
	 * @param userNumber
	 * @return
	 */
	public CertStatus findUserCertStatus(String userNumber);
	
	/**
	 * 
	 * @param usernumber
	 *            当前登录登录用户编号
	 * @param inputusernumber
	 *            用户输入编号
	 * @return
	 */
	public String findChannelCertRealName(String usernumber);
	
	public String findChannelCompanyName(String usernumber);
	
	// onembay
	public List<User> finaAllAuthApply(CertificateQO certificateQO, PageInfo pageinfo);
	
	public ChannelProperty findUserPropertyByUid(int userid);
	
	public User findUserByUserId(long userId);
	
	public void updateUserByUserId(int userId, String certState);
	
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
	public int uploadPortrait(String portrait, String userNumber);
	
	/**
	 * 查询用户编号对应的头像图片
	 * @param userNumber
	 * @return
	 */
	public String findPortrait(String userNumber);
	
}
