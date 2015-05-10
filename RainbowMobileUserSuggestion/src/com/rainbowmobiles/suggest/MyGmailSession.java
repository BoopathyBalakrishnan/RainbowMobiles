/**
 * 
 */
package com.rainbowmobiles.suggest;

import java.util.Properties;

import javax.mail.Session;

import com.rainbowmobiles.databasehelpers.MailModel;

/**
 * @author Nanjundan
 * 
 */
public class MyGmailSession {

	MailModel info = new MailModel();

	/**
	 * Method to get the MyGmail session to send an email
	 * 
	 * @return
	 */
	public  Session getMyGmailSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(info
								.getUserName(), info.getPassword());
					}
				});
		return session;
	}
}
