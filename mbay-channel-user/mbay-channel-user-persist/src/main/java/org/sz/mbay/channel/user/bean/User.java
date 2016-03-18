package org.sz.mbay.channel.user.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;
import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;
import org.sz.mbay.channel.useraccount.bean.UserAccount;

/**
 * 商户信息表
 * 
 * @author
 * 
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	
	/* 用户ID */
	private int id;
	
	/* 实名认证真实 姓名 */
	private String name;
	
	/* 对应的渠道商信息ID* */
	private int channelid;
	
	/* 账户类型 */
	private ChannelProperty property;
	
	/* Email */
	private String email;
	
	/* 账户名 */
	@NotBlank
	@Size(min = 6, max = 30)
	private String username;
	
	/* 密码 */
	@NotBlank
	@Size(min = 6, max = 20)
	private String password;
	
	/* 开户时间 */
	private Timestamp createtime;
	
	/* 密钥 */
	private String privatekey;
	
	/* 用户编号 */
	private String usernumber;
	
	/* 上级编号 */
	@NotBlank
	@Size(min = 8, max = 8)
	private String supnumber;
	
	private int accountid;
	
	/* 实名状态 */
	private CertStatus certStatus;
	
	/* 账户 */
	private UserAccount account;
	
	public UserAccount getAccount() {
		return account;
	}
	
	public void setAccount(UserAccount account) {
		this.account = account;
	}
	
	public CertStatus getCertStatus() {
		return certStatus;
	}
	
	public void setCertStatus(CertStatus authstate) {
		this.certStatus = authstate;
	}
	
	public String getSupnumber() {
		return supnumber;
	}
	
	public void setSupnumber(String supnumber) {
		this.supnumber = supnumber;
	}
	
	public String getUsernumber() {
		return usernumber;
	}
	
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}
	
	public String getPrivatekey() {
		return privatekey;
	}
	
	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}
	
	public int getChannelid() {
		return channelid;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ChannelProperty getProperty() {
		return property;
	}
	
	public void setProperty(ChannelProperty property) {
		this.property = property;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAccountid() {
		return accountid;
	}
	
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	
	public static String getRealNameWithEncrypt(String userName) {
		if (!StringUtils.isEmpty(userName)) {
			if (userName.length() > 4) {
				userName = userName.substring(0, 4) + "***";
			}
			return userName;
		}
		return "";
	}
	
}
