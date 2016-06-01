package com.onecloud.huangqg;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class JSoupCollector {

    public static JavaMailSenderImpl setSender(){
    	
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
    	
    	return mailSender;
    }
    
    public static String senttoWKG(String name) throws MessagingException {  
		
		String email = "wongkiongong@163.com";
		String nickname = "wongkiongong";

		JavaMailSenderImpl mailSender = setSender();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
		help.setFrom("wongkiongong@163.com");
		help.setTo(new String[] { "wongkiongong@163.com", email });
		help.setSubject("交易提醒");
		String content = name;
		help.setText(content, true);
		mailSender.send(message);
		System.out.println("Send to " + email);
		return "Mail sent to " + nickname;
    }
    
    public static String senttoHQG(String info) throws MessagingException {  
		
		String email = "huangqg@onecloud.cn";
		String nickname = "huangqg";

		JavaMailSenderImpl mailSender = setSender();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
		help.setFrom("wongkiongong@163.com");
		help.setTo(new String[] { "huangqg@onecloud.cn", email });
		help.setSubject("交易提醒");
		String content = info;
		help.setText(content, true);
		mailSender.send(message);
		System.out.println("Send to " + email);
		return "Mail sent to " + nickname;
    } 
	
	public static void main(String args[]){
		
		while(true){
			float goldSell = 0.0f;
			float silverSell = 0.0f;
			try {
				Document doc = Jsoup.connect("http://www.icbc.com.cn/ICBCDynamicSite/Charts/GoldTendencyPicture.aspx").get();
				Element table = doc.getElementById("TABLE1");
				
	            Element tbodyNode = table.child(0);
	//            String body = tbodyNode.data();
	//            String text = tbodyNode.html();
	            if(tbodyNode != null) {
	//            	List<Node> trsNodes = tbodyNode.childNodes();
	                for (int i = 1; i <= tbodyNode.childNodeSize()/2; i++) {
	                    //Node trnode = (Node) trsNodes.elementAt(i);
	                    
	                    Node trnode = (Node) tbodyNode.childNode(2*i-1);
	                    String firstThText = ((Element)trnode.childNode(1)).html();
	                    if("人民币账户黄金".equals(firstThText)){
	//                    	NodeList tdNodes = trnode.getChildren();
	                    	for(int j = 1; j <= trnode.childNodeSize()/2; j ++){
	                    		System.out.println(j + ":" + ((Element)trnode.childNode(2*j-1)).html());
	                            System.out.println("=================================================");
	                            if(j == 4){
	                            	goldSell = Float.valueOf(((Element)trnode.childNode(2*j-1)).html());
	                            }
	                    	}
	                    }
	                    if("人民币账户白银".equals(firstThText)){
	//                    	NodeList tdNodes = trnode.getChildren();
	                    	for(int j = 1; j <= trnode.childNodeSize()/2; j ++){
	                    		System.out.println(j + ":" + ((Element)trnode.childNode(2*j-1)).html());
	                            System.out.println("=================================================");
	                            if(j == 4){
	                            	silverSell = Float.valueOf(((Element)trnode.childNode(2*j-1)).html());
	                            }
	                    	}
	                    }
	                }
	            } 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(goldSell <= 225){
				try {
					senttoWKG("人民币账户黄金价格低于:" + goldSell);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(silverSell <= 3.10){
				try {
					senttoWKG("人民币账户白银价格低于:" + silverSell);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			System.out.println("Sell price is: " + sell);
		
			try {
	            Thread.sleep(30000);
	        } catch (InterruptedException e) {
	            e.printStackTrace(); 
	        }
		}
	}
}
