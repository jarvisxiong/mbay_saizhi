package org.sz.mbay.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MbayEmailServiceImpl implements MbayEmailService {
	
	private JavaMailSender mailSender;
	
	private String systemEmail;
	
	@Override
	public void sendMail(String to, String subject, String htmlText) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, "utf-8");
		try {
			mimeHelper.setFrom(systemEmail);
			mimeHelper.setTo(to);
			mimeHelper.setSubject(subject);
			mimeHelper.setText(htmlText, true);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			
		}
		
	}
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public String getSystemEmail() {
		return systemEmail;
	}
	
	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
	
}
