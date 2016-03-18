package org.sz.mbay.traffic.component.mail;

import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MbayMail {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MbayMail.class);
	private MbayMail() {

	}
	private static MimeMessage mimessage=null;
	public static MimeMessage getMimemessage(){
		if(mimessage==null){
			Session session=null;
		    Properties properties=null;
		    Authenticator authen=null;
			InputStream stream=MbayMail.class.getResourceAsStream("/mail.xml");	
			//SAXReader用于解析xml文件
			SAXReader saxreader=new SAXReader();
			try {
				Document document=saxreader.read(stream);
				Element root=document.getRootElement();
				String smtphost=root.elementText("smtphost");
				String smtpprot=root.elementText("smtpprot");
				String fromemile=root.elementText("fromemile");
				String username=root.elementText("username");
				String pwd=root.elementText("password");
				properties=new Properties();
				properties.put("mail.smtp.host", smtphost);
				properties.put("mail.smtp.port", smtpprot);
				properties.put("mail.smtp.auth", "true");
				authen=new MbayAuthenticator(username,pwd);			
			    session=Session.getDefaultInstance(properties,authen);
				Address address=new InternetAddress(fromemile,fromemile);//建立邮件地址
				mimessage=new MimeMessage(session);
				mimessage.setFrom(address);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mimessage;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	/**
	 * @param args
	 * @throws Exception
	 */
	/**
	 * @param args
	 * @throws Exception
	 */
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// if(true){
		// MbayMailMsg message=new MbayMailMsg("798526861@qq.com","你好","你好");
		//
		// sendHTMLEmile(message);
		// return;
		// }

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.mbqianbao.com");
		properties.put("mail.smtp.port", 25);
		properties.put("mail.smtp.auth", "true");

		Authenticator auth = new MbayAuthenticator("noreply@mbqianbao.com",
				"MeiBei1o71o8");

		Session session = Session.getDefaultInstance(properties, auth);
		MimeMessage message = new MimeMessage(session);

		message.setContent("Hello", "text/plain");// 设置part类型
		message.setSubject("");// 设置主题
		Address address = new InternetAddress("noreply@mbqianbao.com",
				"noreply@mbqianbao.com");// 建立邮件地址
		Address toaddress = new InternetAddress("798526861@qq.com.com");// 收件人。
		Address cc = new InternetAddress("yihanzhiming@sina.com");// 收件人。
		message.setFrom(address);

		message.setRecipient(Message.RecipientType.TO, toaddress);// 收信人。
		message.setRecipient(Message.RecipientType.CC, cc);// 收信人。

	}

	public static void sendTextEmil(MbayMailMsg message) {
		try {
//			JavaMailSenderImpl mbayMailSender = (JavaMailSenderImpl) SpringApplicationContext
//					.getBean("mbayMailSender");
			MimeMessage mimessage =getMimemessage();
			mimessage.setContent(message.getContent(),
					"text/plain; charset=utf-8");
			mimessage.setSubject(message.getSubject());// 设置主题
			Address toaddress = new InternetAddress(message.getEmile());// 收件人。
			mimessage.setRecipient(Message.RecipientType.TO, toaddress);
			if (message.getCcemile() != null && message.getCcemile().length > 0) {
				Address[] ccaddresses = new InternetAddress[message
						.getCcemile().length];
				for (int i = 0; i < message.getCcemile().length; i++) {
					String emileaddress = message.getCcemile()[i];
					Address ccaddress = new InternetAddress(emileaddress);// 抄送。
					ccaddresses[i] = ccaddress;

				}
				mimessage.setRecipients(Message.RecipientType.CC, ccaddresses);

			}
			MbayMmailThread thred = new MbayMmailThread(mimessage);
			thred.start();
		} catch (MessagingException e) {
			e.fillInStackTrace();
		}
	}

	public static void sendHTMLEmile(MbayMailMsg message) {

		try {
//			JavaMailSenderImpl mbayMailSender = (JavaMailSenderImpl) SpringApplicationContext
//					.getBean("mbayMailSender");
			MimeMessage mimessage =getMimemessage();
			System.out.println(message.getContent());
			mimessage.setContent(message.getContent(),
					"text/html; charset=utf-8");
			mimessage.setSubject(message.getSubject());// 设置主题
			Address toaddress = new InternetAddress(message.getEmile());// 收件人。
			mimessage.setRecipient(Message.RecipientType.TO, toaddress);
			// /Transport.send(mimessage);

			MbayMmailThread thred = new MbayMmailThread(mimessage);
			thred.start();

		} catch (MessagingException e) {
		}
	}

	/**
	 * 发送带附件的邮件
	 * 
	 * @param message
	 * @throws Exception
	 */
	public static void setMailWithAttachments(MbayMailMsg message,
			String filepath) throws Exception {
//		JavaMailSenderImpl mbayMailSender = (JavaMailSenderImpl) SpringApplicationContext
//				.getBean("mbayMailSender");
		MimeMessage mimessage =getMimemessage();
		mimessage.setSubject(message.getSubject());// 设置主题
		Address toaddress = new InternetAddress(message.getEmile());// 收件人。
		if (message.getCcemile() != null) {
			String toListcs=getMailList(message.getCcemile());
			@SuppressWarnings("static-access")
			InternetAddress[] iaToListcs = new InternetAddress().parse(toListcs);
			mimessage.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
		}
		mimessage.setRecipient(Message.RecipientType.TO, toaddress);
		BodyPart bodyPart = new MimeBodyPart(); // 保存文本
		bodyPart.setContent(message.getContent(), "text/html; charset=utf-8");
		Multipart multipart = new MimeMultipart(); // 新建一个MimeMultipart对象用来存放BodyPart对象
		multipart.addBodyPart(bodyPart);
		MimeBodyPart attachPart = new MimeBodyPart();
		FileDataSource filedatasource = new FileDataSource(filepath);
		attachPart.setDataHandler(new DataHandler(filedatasource));
		try {
			attachPart.setFileName(MimeUtility.encodeText(filedatasource
					.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		multipart.addBodyPart(attachPart);
		mimessage.setContent(multipart);
		// /Transport.send(mimessage);
		MbayMmailThread thred = new MbayMmailThread(mimessage);
		thred.start();

	}

	private static String getMailList(String[] mailArray) {

		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}

			}
		}
		return toList.toString();

	}

	static class MbayMmailThread extends Thread {
//		JavaMailSenderImpl mbayMailSender = (JavaMailSenderImpl) SpringApplicationContext
//				.getBean("mbayMailSender");
		private MimeMessage mimessage = null;

		public MbayMmailThread(MimeMessage mimessage) {
			this.mimessage = mimessage;
		}

		@Override
		public void run() {
			try {
				Transport.send(mimessage);
			} catch (MessagingException e) {
				LOGGER.error("",e.fillInStackTrace());
			}
		}

	}

}
