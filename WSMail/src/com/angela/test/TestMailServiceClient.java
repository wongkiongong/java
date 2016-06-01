/**
  * 
  */
 package com.angela.test;
 
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 
 import org.junit.Before;
 import org.junit.Test;
 
 import angela.com.ArrayOfString;
 
 /**
  * @Program Name : testMailClient.com.angela.test.TestMailServiceClient.java
  * @Written by : kzw
  * @version : v1.00
  * @Creation Date : 2012-7-18 下午05:00:47
  * @Description :
  * 
  * @ModificationHistory Who When What ------- ------------- -----------------
  *                      kzw 2012-7-18 下午05:00:47 create
  * 
  **/
 public class TestMailServiceClient {
     private mailClient serivce;
     List<String> transmitMails;
     List<String> takeMail;
     List<String> multiparts;
     @Before
     public void testInit() {
         serivce = new mailClient();
         transmitMails = new ArrayList<String>();
         takeMail = new ArrayList<String>();
         multiparts = new ArrayList<String>();
         takeMail.add("100086@qq.com");
 
     }
 
     @Test
     public void testMailSend(){
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String subject = sdf.format(new Date()) + " subject";
         String content = sdf.format(new Date()) + "content 中文内容"; 
         String sendMailAddr = "kuangzhongwei@1872.net";
         String sendMailPwd = "XXXXX";
         ArrayOfString ccs=new ArrayOfString();
         ArrayOfString tos=new ArrayOfString();
         ArrayOfString multipartss=new ArrayOfString();
         tos.getString().addAll(takeMail);
         
         serivce.getmailHttpPort().sendMail(null, null, ccs, tos, 
                     subject, content, multipartss, "02","1872", "测试");
         
     }
 }