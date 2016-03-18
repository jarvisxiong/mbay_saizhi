package org.sz.mbay.traffic.component.mail;

public class MbayMailMsg {
	
	/**发送邮箱*****/
	private String emile;
	/**抄送邮箱*****/
	private String[] ccemile;
	
	/**主题****/
	private String subject;
	
	/**内容******/
	private String content;

	public String getEmile() {
		return emile;
	}
	
	public MbayMailMsg(){
		
	}
	
	/**
	 * @param emile 发送邮箱
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	public MbayMailMsg(String emile,String[]ccmail,String subject,String content){
		this.setContent(content);
		this.setEmile(emile);
		this.setSubject(subject);
		this.setCcemile(ccmail);
	}
	
	public void setEmile(String emile) {
		this.emile = emile;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	} 

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getCcemile() {
		return ccemile;
	}

	public void setCcemile(String[] ccemile) {
		this.ccemile = ccemile;
	}

}
