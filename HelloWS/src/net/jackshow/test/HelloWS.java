package net.jackshow.test;

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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.util.Properties; 

//指定URI 
@Path("/helloworld") 
public class HelloWS { 
        //处理HTTP的GET请求 
        @GET 
        // 处理请求反馈的内容格式为"text/plain" 
        @Produces("text/plain") 
        public String getClichedMessage() throws MessagingException { 
        	
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
    		String content = nickname + "有人来了！";
    		help.setText(content, true);
    		mailSender.send(message);
            return "Mail sent!"; 
        }

        public static void main(String[] args) throws IOException { 
                //创建RESTful WebService服务 
                HttpServer server = HttpServerFactory.create("http://localhost:9999/"); 
                //启动服务，这会导致新开一个线程 
                server.start(); 
                //输出服务的一些提示信息到控制台 
                System.out.println("RESTful WebService服务已经启动"); 
                System.out.println("服务访问地址: http://localhost:9999/helloworld"); 
        } 
}
