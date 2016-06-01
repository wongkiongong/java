package com.angela.server;
 
 import java.util.List;
 
 /**
  * 邮件接口
  * @Program Name : angelaws.com.angela.server.IMailService.java
  * @Written by : kzw
  * @version : v1.00
  * @Creation Date : 2012-7-17 上午09:29:30
  * @Description :
  * 邮件接口
  * @ModificationHistory 
  * Who             When         What 
  * ------- -------------     ----------------- 
  * kzw    2012-7-17 上午09:29:30         create
  * 
  **/
 public interface IMailService {
     /**
      * 字符集配置key,这里有点怪怪的， 以后优化//TODO....
      */
     public final String CHARACTERCONFIG = "character";
     public final String SMTPCONFIG = "smtp";
     public final String CHECKSEND = "checkSend";
     public final String DEFAULT_SEND = "defaultSend";
     public final String DEFAULT_SEND_PWD = "password";
     /**
      * 发送邮件
      * @Eclosing_Method  : sendMail
      * @author  by       : kzw
      * @creation Date    : 2012-7-17上午10:08:50
      * @param sendMailAddr 发送人邮箱
      * @param sendMailPwd 发送人密码
      * @param cc 抄送人
      * @param to 收件人
      * @param subject 邮件主题
      * @param content 邮件内容
      * @param accessories 附件
      * @param mailType 邮件类型
      * @param operMan 操作员
      * @param memo 备注
      *
      */
     void sendMail(String sendMailAddr, String sendMailPwd, List<String> cc, 
             List<String> to, String subject, String content, List<String> accessories,
             String mailType, String operMan, String memo);
     
 }