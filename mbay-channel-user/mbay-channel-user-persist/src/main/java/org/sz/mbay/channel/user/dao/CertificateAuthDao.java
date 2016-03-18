package org.sz.mbay.channel.user.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.user.bean.Certificate;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.qo.AuthStateQO;

/**
 * 实名认证Dao
 * 
 * @author Fenlon
 * 
 */
public interface CertificateAuthDao extends BaseDao<Certificate> {
	
	/**
	 * @param userid
	 * @param authstate
	 * @return
	 */
	public int updateCertStatus(long userid, CertStatus authstate);
	
	/**
	 * @param userid
	 * @return
	 */
	public CertStatus getUserCertStatus(String userNumber);
	
	/**
	 * @param userid
	 * @return
	 */
	public Certificate findCertificate(long userid);
	
	// 企业
	/**
	 * @param userNumber
	 * @return
	 */
	public ChannelInfo findChannelInfo(String userNumber);
	
	/**
	 * 删除
	 * 
	 * @param certificateId
	 * @return
	 * @throws Exception
	 */
	public int deleteCertiticateByUid(long uid) throws Exception;
	
	/**
	 * 删除企业实名认证信息
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public int deleteChannelInfoByUid(long uid) throws Exception;
	
	public Certificate findCertificate(String userName);
	
	/**
	 * 获取商户名称
	 * 
	 * @param usernumber
	 * @return
	 */
	public String getEnterpriseName(String usernumber);
	
	/**
	 * 获取企业商户实名认证电话
	 * 
	 * @param usernumber
	 * @return
	 */
	public String findPhoneOfChannel(String usernumber);
	
	/**
	 * 获取个人实名认证电话
	 * 
	 * @param usernumber
	 * @return
	 */
	public String findPhoneOfPersonal(String usernumber);
	
	/**
	 * 获取企业商户实名认证邮箱
	 * 
	 * @param usernumber
	 * @return
	 */
	public String findEmailOfChannel(String usernumber);
	
	/**
	 * 获取个人实名认证邮箱
	 * 
	 * @param usernumber
	 * @return
	 */
	public String findEmailOfPersonal(String usernumber);
	
	/**
	 * 更新认证
	 * @param authStateQO
	 * @return
	 */
	public int updateCertificate(AuthStateQO authStateQO);
	
}
