package com.zyinf.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class EmailSender {
	/**
	 * Spring
	 */
	private String smtpHost;
	private String smtpFrom;
	private String smtpAuthUser;
	private String smtpAuthPwd;

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public void setSmtpFrom(String smtpFrom) {
		this.smtpFrom = smtpFrom;
	}
	public void setSmtpAuthUser(String smtpAuthUser) {
		this.smtpAuthUser = smtpAuthUser;
	}
	public void setSmtpAuthPwd(String smtpAuthPwd) {
		this.smtpAuthPwd = smtpAuthPwd;
	}
	
	private MimeMessage makeMimeMessage(Session session, String subject, InputStream is, String[] attachFiles, Address[] toUsers) 
			throws AddressException, MessagingException, IOException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(smtpFrom));
		message.addRecipients(Message.RecipientType.TO, toUsers);
		message.setSubject(subject);		
		message.setSentDate(new Date());
		
		Multipart multipart = new MimeMultipart();  
        
		// 添加正文
		MimeBodyPart bodyPart = new MimeBodyPart();  
        bodyPart.setDataHandler(new DataHandler(
        		new ByteArrayDataSource(is, "text/html; charset=UTF-8")));
        multipart.addBodyPart(bodyPart);    
        
        // 添加附件
        if(attachFiles != null) {  
            for(int i = 0; i < attachFiles.length; i++) {
            	MimeBodyPart part = new MimeBodyPart();
            	FileDataSource ds = new FileDataSource(attachFiles[i]);
            	part.setDataHandler(new DataHandler(ds));
            	
            	// 如果直接使用 part.setFileName(ds.getName())，当文件名是中文名时存在问题
            	try {
					part.setFileName(MimeUtility.encodeText(ds.getName()));
				} 
            	catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            	multipart.addBodyPart(part);
            }
        }   
        message.setContent(multipart);
        message.saveChanges();
        return message;
	}
	
	private MimeMessage makeMimeMessage(Session session, String subject, String content, String[] attachFiles, Address[] toUsers) 
		throws AddressException, MessagingException, IOException {
			return makeMimeMessage(session, subject, new ByteArrayInputStream(content.getBytes()), attachFiles, toUsers);
	}
	
	public void send(String subject, String content, String[] attachFiles, Address[] toUsers) 
			throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props);
		session.setDebug(false);
		
		MimeMessage message = makeMimeMessage(session, subject, content, attachFiles, toUsers);	
		
		Transport transport = session.getTransport("smtp");
		transport.connect(smtpHost, smtpAuthUser, smtpAuthPwd);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public static void main(String[] args) throws AddressException, MessagingException, IOException {
		EmailSender sender = new EmailSender();
		sender.setSmtpAuthPwd("stevens1q");
		sender.setSmtpAuthUser("13816888783@139.com");
		sender.setSmtpFrom("13816888783@139.com");
		sender.setSmtpHost("smtp.139.com");
//		sender.setSmtpAuthPwd("stevens1!");
//		sender.setSmtpAuthUser("eimpert@aliyun.com");
//		sender.setSmtpFrom("eimpert@aliyun.com");
//		sender.setSmtpHost("smtp.aliyun.com");
		sender.send("折扣清单_" + Util.dateToString(new Date()), 
				"a list from cc", null,
				new Address[] {new InternetAddress("eimpert@aliyun.com")});
		System.out.println("send email");

	}
}
