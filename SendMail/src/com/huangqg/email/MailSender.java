package com.huangqg.email;

import java.util.Properties;

import javax.jws.WebService;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.ws.Endpoint;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@WebService
public class MailSender {

	//该方法就是要暴露给其他应用程序调用的方法  
    public String transWords(String words){  
        String res="";  
        for(char ch : words.toCharArray()){  
            res+="\t"+ch+"\t";  
        }  
        return res;  
    }
    
    public void SendMail() throws MessagingException{
    	
		String host = "smtp.163.com";
		int port = 25;
		String username = "wongkiongong@163.com";
		String password = "amwxkjiqasd";

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.setJavaMailProperties(javaMailProperties);
		String email = "huangqg@onecloud.cn";
		String nickname = "huangqg";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
		help.setFrom("wongkiongong@163.com");
		help.setTo(new String[] { "huangqg@onecloud.cn", email });
		help.setSubject("测试");
		String content = "你好" + nickname;
		help.setText(content, true);
		mailSender.send(message);
    	
    }
	
	
	// test sending email from wongkiongong@163.com to huangqg@onecloud.cn
	public static void main(String[] args) {

		Endpoint.publish("http://localhost:9001/Service/MailSender",new MailSender());
		System.out.println("Publish Success!");
	}

}