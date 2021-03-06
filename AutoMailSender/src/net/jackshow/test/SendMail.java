package net.jackshow.test;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/*import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/helloWorld")

public class HelloWS {
 @GET
 @Produces(MediaType.TEXT_PLAIN)
 public String helloWorld(){
  String ret = "Hello World!";
  return ret;
 }
}*/

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer; 

//指定URI 
@Path("/sendmail") 
public class SendMail { 
	    
	    public static JavaMailSenderImpl setSender(){
	    	
    		String host = "smtp.163.com";
    		int port = 25;
    		String username = "wongkiongong@163.com";
    		String password = "amwxkjiqasd";
	    	
/*    		String host = "smtp.sina.com";
    		int port = 25;
    		String username = "wongkiongong@sina.com";
    		String password = "amwxkjiq";*/

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
	
        //处理HTTP的GET请求 
        @GET 
        // 处理请求反馈的内容格式为"text/plain" 
        @Produces("text/plain") 
        public String getClichedMessage() throws MessagingException { 
 
            return "Welcome to My home!"; 
        }
        
        @GET  
        @Path("hwh")  
        @Produces("text/plain")  
        public static String senttoHWH(@PathParam("name") String name) throws MessagingException {  
            		
    		String email = "1586575816@qq.com";
    		String nickname = "妇产科";

    		JavaMailSenderImpl mailSender = setSender();
    		MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
    		help.setFrom("wongkiongong@163.com");
    		help.setTo(new String[] { "1586575816@qq.com", email });
    		help.setSubject("测试");
    		String content = nickname + " 柳柳来了！";
    		help.setText(content, true);
    		mailSender.send(message);
    		System.out.println("Send to " + email);
    		return "Mail sent to " + email;
        } 

        @GET  
        @Path("cmy")  
        @Produces("text/plain")  
        public String senttoCMY(@PathParam("name") String name) throws MessagingException {  
            		
    		String email = "myabc2468@qq.com";
    		String nickname = "chenmy";

    		JavaMailSenderImpl mailSender = setSender();
    		MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
    		help.setFrom("wongkiongong@163.com");
    		help.setTo(new String[] { "huangqg@onecloud.cn", email });
    		help.setSubject("扫描触发");
    		String content = nickname + "你好！";
    		help.setText(content, true);
    		mailSender.send(message);
    		System.out.println("Send to " + email);
    		return "Mail sent to " + nickname;
        } 

        @GET  
        @Path("ljx")  
        @Produces("text/plain")  
        public static String senttoLJX(@PathParam("name") String name) throws MessagingException {  
            		
    		String email = "linjx@onecloud.cn";
    		String nickname = "linjx";

    		JavaMailSenderImpl mailSender = setSender();
    		MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
    		help.setFrom("wongkiongong@163.com");
    		help.setTo(new String[] { "linjx@onecloud.cn", email });
    		help.setSubject("提醒");
    		String content = nickname + " 你好！";
    		help.setText(content, true);
    		mailSender.send(message);
    		System.out.println("Send mail to " + email);
    		return "please check your email:" + email;
        } 
        
        @GET  
        @Path("an")  
        @Produces("text/plain")  
        public static String senttoAN(@PathParam("name") String name) throws MessagingException {  
            		
    		String email = "liuwsh@onecloud.cn";
    		String nickname = "niuniu";

    		JavaMailSenderImpl mailSender = setSender();
    		MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
    		help.setFrom("wongkiongong@163.com");
    		help.setTo(new String[] { "liuwsh@onecloud.cn", email });
    		help.setSubject("提醒");
    		String content = nickname + " 你好！";
    		help.setText(content, true);
    		mailSender.send(message);
    		System.out.println("Send mail to " + email);
    		return "please check your email:" + email;
        } 
        

        @GET  
        @Path("wkg")  
        @Produces("text/plain")  
        public static String senttoWKG(@PathParam("name") String name) throws MessagingException {  
            		
    		String email = "wongkiongong@163.com";
    		String nickname = "wongkiongong";

    		JavaMailSenderImpl mailSender = setSender();
    		MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
    		help.setFrom("wongkiongong@163.com");
    		help.setTo(new String[] { "wongkiongong@163.com", email });
    		help.setSubject("提醒");
    		String content = nickname + " 你好！";
    		help.setText(content, true);
    		mailSender.send(message);
    		System.out.println("Send mail to" + email);
    		return "please check your email:" + email;
        }
        
        @GET  
        @Path("hqg")  
        @Produces("text/plain")  
        public String senttoHQG(@PathParam("name") String name) throws MessagingException {  
            		
    		String email = "huangqg@onecloud.cn";
    		String nickname = "huangqg";

    		JavaMailSenderImpl mailSender = setSender();
    		MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper help = new MimeMessageHelper(message, true, "UTF-8");
    		help.setFrom("wongkiongong@163.com");
    		help.setTo(new String[] { "huangqg@onecloud.cn", email });
    		help.setSubject("测试");
    		String content = nickname + "你好！";
    		help.setText(content, true);
    		mailSender.send(message);
    		System.out.println("Send to " + email);
    		return "Mail sent to " + nickname;
        } 

        public static void main(String[] args) throws IOException { 
                //创建RESTful WebService服务 
                HttpServer server = HttpServerFactory.create("http://localhost:9999/"); 
                //启动服务，这会导致新开一个线程 
                server.start(); 
                //输出服务的一些提示信息到控制台 
                System.out.println("RESTful WebService服务已经启动"); 
                System.out.println("服务访问地址: http://localhost:9999/sendmail"); 
        } 
}
