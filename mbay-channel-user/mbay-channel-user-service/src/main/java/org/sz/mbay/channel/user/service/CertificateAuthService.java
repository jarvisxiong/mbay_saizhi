package org.sz.mbay.channel.user.service;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.bean.Certificate;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.qo.AuthStateQO;

public interface CertificateAuthService extends BaseService {
	
	public Certificate addCertiticate(Certificate certificate);
	
	public void addEnterpriseAuthInfo(ChannelInfo channelInfo);
	
	/**
	 * 实名认证状态
	 * 
	 * @param userid
	 * @return
	 */
	public CertStatus getUserCertStatus(String userNumber);
	
	public Certificate findCertificate(long userid);
	
	// 企业审核操作
	public ExecuteResult addChannelinfo(ChannelInfo channelinfo);
	
	public ChannelInfo findChannelInfo(String userNumber);
	
	public Certificate findCertificate(String userName);
	
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
	 * 获取商家名称
	 * 
	 * @param usernumber
	 * @return
	 */
	public String getEnterpriseName(String usernumber);
	
	/**
	 * 更新认证
	 * @param authStateQO
	 * @return
	 */
	public int updateCertificate(AuthStateQO authStateQO);
}
