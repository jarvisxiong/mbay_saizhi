package org.sz.mbay.traffic.component.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MbayAuthenticator extends Authenticator{

    private final String username;

    private final String pwd;

    public MbayAuthenticator(String username,String pwd){
        this.username=username;
        this.pwd=pwd;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(this.username,this.pwd);
    }

}
