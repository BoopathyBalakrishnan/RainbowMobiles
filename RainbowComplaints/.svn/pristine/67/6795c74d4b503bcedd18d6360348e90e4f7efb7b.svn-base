/**
 * 
 */
package com.rainbowmobiles.app.complaints.util;

import java.util.Properties;

import javax.mail.Session;

import com.rainbowmobiles.app.complaints.constants.ApplicationConstants;

/**
 * @author Nanjundan
 * 
 */
public class MyGmailSession {

	/**
	 * Method to get the MyGmail session to send an email
	 * @return
	 */
	public static Session getMyGmailSession() {
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
						return new javax.mail.PasswordAuthentication(
								ApplicationConstants.GMAIL_SESSION_USERNAME,
								ApplicationConstants.GMAIL_SESSION_PASSWORD);
					}
				});
		return session;
	}
}
