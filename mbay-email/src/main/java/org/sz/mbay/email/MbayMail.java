package org.sz.mbay.email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MbayMail {

	private static MbayEmailService emailService;

	public static void sendMail(String to, String subject, String htmlText) {
		getMbayEmailService().sendMail(to, subject, htmlText);
	}

	private static MbayEmailService getMbayEmailService() {
		if (emailService == null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("mbay-email.xml");
			emailService = (MbayEmailService) ctx.getBean(MbayEmailService.class);
		}
		return emailService;
	}

}
