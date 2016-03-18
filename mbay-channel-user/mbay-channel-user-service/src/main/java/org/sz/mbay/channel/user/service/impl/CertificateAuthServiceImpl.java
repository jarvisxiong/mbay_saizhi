package org.sz.mbay.channel.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.channel.user.bean.Certificate;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.dao.CertificateAuthDao;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.qo.AuthStateQO;
import org.sz.mbay.channel.user.service.CertificateAuthService;

@Service("CertificateAuthServiceImpl")
public class CertificateAuthServiceImpl extends BaseServiceImpl implements CertificateAuthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CertificateAuthServiceImpl.class);
	
	@Autowired
	CertificateAuthDao certificatedao;
	
	@Override
	@Transactional
	public Certificate addCertiticate(Certificate certificate) {
		try {
			// TODO:1需要解决
			
			/*
			 * // 用户已登录，表示重新申请实名认证
			 * if (ChannelContext.getSessionChannel() != null
			 * && certificate != null) {
			 * this.certificatedao.deleteCertiticateByUid(ChannelContext
			 * .getSessionChannel().getId());
			 * }
			 */
			certificate.setAuthstate(CertStatus.UNDERREVIEW);
			certificate = certificatedao.createBean(certificate);
			certificatedao.updateCertStatus(certificate.getUserid(), CertStatus.UNDERREVIEW);
			return certificate;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("addCertiticate", e.fillInStackTrace());
		}
		return null;
	}
	
	@Override
	public void addEnterpriseAuthInfo(ChannelInfo channelInfo) {
		try {
			this.certificatedao.createBean(channelInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public CertStatus getUserCertStatus(String userNumber) {
		return certificatedao.getUserCertStatus(userNumber);
	}
	
	@Override
	public Certificate findCertificate(long userid) {
		return this.certificatedao.findCertificate(userid);
	}
	
	@Override
	public Certificate findCertificate(String userName) {
		return this.certificatedao.findCertificate(userName);
	}
	
	/**
	 * 企业认证
	 */
	@Override
	@Transactional
	public ExecuteResult addChannelinfo(ChannelInfo channelinfo) {
		try {
			// 用户已登录表明为重新申请 删除原实名信息
			
			// TODO:1下面代码被注释 需要解决
			/*
			 * if (ChannelContext.getSessionChannel() != null) {
			 * this.certificatedao.deleteChannelInfoByUid(ChannelContext.getSessionChannel().getId());
			 * }
			 */
			channelinfo.setAuthstate(CertStatus.UNDERREVIEW);
			// this.photoDao.createBean(channelinfo.getCopyLicense());
			// this.photoDao.createBean(channelinfo.getCopyCode());
			// this.photoDao.createBean(channelinfo.getCopyTax());
			channelinfo = certificatedao.createBean(channelinfo);
			certificatedao.updateCertStatus(channelinfo.getUserid(), CertStatus.UNDERREVIEW);
			return new ExecuteResult(true, "", "");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOGGER.error("addChannelinfo", e.fillInStackTrace());
			return new ExecuteResult(false, "", "认证信息提交失败，请重试！");
		}
	}
	
	@Override
	public ChannelInfo findChannelInfo(String userNumber) {
		return this.certificatedao.findChannelInfo(userNumber);
	}
	
	@Override
	public String getEnterpriseName(String usernumber) {
		return certificatedao.getEnterpriseName(usernumber);
	}
	
	@Override
	public String findPhoneOfChannel(String usernumber) {
		return this.certificatedao.findPhoneOfChannel(usernumber);
	}
	
	@Override
	public String findPhoneOfPersonal(String usernumber) {
		return this.certificatedao.findPhoneOfPersonal(usernumber);
	}
	
	@Override
	public String findEmailOfChannel(String usernumber) {
		return this.certificatedao.findEmailOfChannel(usernumber);
	}
	
	@Override
	public String findEmailOfPersonal(String usernumber) {
		return this.certificatedao.findEmailOfPersonal(usernumber);
	}
	
	@Override
	public int updateCertificate(AuthStateQO authStateQO) {
		return this.certificatedao.updateCertificate(authStateQO);
	}
}
