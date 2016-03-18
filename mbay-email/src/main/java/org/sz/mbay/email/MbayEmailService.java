package org.sz.mbay.email;


public interface MbayEmailService {
	
	 void sendMail( String to, String subject, String htmlText );

}
