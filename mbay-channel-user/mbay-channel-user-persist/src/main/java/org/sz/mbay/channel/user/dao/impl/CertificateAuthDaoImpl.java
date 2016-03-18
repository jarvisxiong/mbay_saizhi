package org.sz.mbay.channel.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.user.bean.Certificate;
import org.sz.mbay.channel.user.bean.ChannelInfo;
import org.sz.mbay.channel.user.dao.CertificateAuthDao;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.qo.AuthStateQO;

@Repository
public class CertificateAuthDaoImpl extends BaseDaoImpl<Certificate> implements CertificateAuthDao {
	
	@Override
	public int updateCertStatus(long userid, CertStatus authstate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("certStatus", authstate.ordinal());
		return super.template.update("updateUsertoAuthed", map);
	}
	
	@Override
	public CertStatus getUserCertStatus(String userNumber) {
		return this.template.selectOne("getUserCertStatus", userNumber);
	}
	
	@Override
	public Certificate findCertificate(long userid) {
		return this.template.selectOne("findCertificate", userid);
	}
	
	@Override
	public ChannelInfo findChannelInfo(String userNumber) {
		return this.template.selectOne("findChannelInfo", userNumber);
	}
	
	@Override
	public Certificate findCertificate(String userName) {
		return this.template.selectOne("findCertificateByName", userName);
	}
	
	@Override
	public int deleteCertiticateByUid(long uid) throws Exception {
		return this.template.delete("deleteCertiticateByUid", uid);
	}
	
	@Override
	public int deleteChannelInfoByUid(long uid) throws Exception {
		return this.template.delete("deleteChannelInfoByUid", uid);
	}
	
	@Override
	public String getEnterpriseName(String usernumber) {
		return this.template.selectOne("getEnterpriseName", usernumber);
	}
	
	@Override
	public String findPhoneOfChannel(String usernumber) {
		return this.template.selectOne("findPhoneOfChannel", usernumber);
	}
	
	@Override
	public String findPhoneOfPersonal(String usernumber) {
		return this.template.selectOne("findPhoneOfPersonal", usernumber);
	}
	
	@Override
	public String findEmailOfChannel(String usernumber) {
		return this.template.selectOne("findEmailOfChannel", usernumber);
	}
	
	@Override
	public String findEmailOfPersonal(String usernumber) {
		return this.template.selectOne("findEmailOfPersonal", usernumber);
	}
	
	@Override
    public int updateCertificate(AuthStateQO authStateQO) {
        return this.template.update("updateCertificate", authStateQO);
    }
}
