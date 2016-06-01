package com.angela.bean;


import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MailBean {
    private Logger log = LogManager.getLogger(MailBean.class);
    /**
     * 主题
     */
    private String subject;    
    /**
     * 发送邮箱地址
     */
    private String sendMail;
    /**
     * 发送邮箱密码
     */
    private String sendMailPassword;
    /**
     * 收件人
     */
    private List<String> tos;
    /**
     * 抄送
     */
    private List<String> ccs;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 内容编码
     */
    private String character;
    /**
     * 发送服务器地址
     */
    private String mailHost;
    /**
     *  附件
     */
    private List<String> accessories;
    /**
     * 发送是否需要校验
     */
    private String isCheckSend;
    /**
     * 
     * @param subject 主题
     * @param sendMail 发件人
     * @param sendMailPassword 密码
     * @param tos 收件人 
     * @param content 内容
     * @param character  内容字符集
     * @param mailHost SMTP主机
     * @param isCheckSend 是否需要验证
     */
    public MailBean(String subject, String sendMail, String sendMailPassword,
            List<String> tos, String content, String character,
            String mailHost, String isCheckSend) {
        this.subject = subject;
        this.sendMail = sendMail;
        this.sendMailPassword = sendMailPassword;
        this.tos = tos;
        this.content = content;
        this.character = character;
        this.mailHost = mailHost;
        this.isCheckSend = isCheckSend;
    }
    /**
     * 
     * @param subject 主题
     * @param sendMail 发件人
     * @param sendMailPassword 密码
     * @param tos 收件人 
     * @param content 内容
     * @param character  内容字符集
     * @param mailHost SMTP主机
     * @param isCheckSend 是否需要验证
     * @param accessories 附件
     */
    public MailBean(String subject, String sendMail, String sendMailPassword,
            List<String> tos, String content, String character,
            String mailHost, List<String> accessories, String isCheckSend) {
        this.subject = subject;
        this.sendMail = sendMail;
        this.sendMailPassword = sendMailPassword;
        this.tos = tos;
        this.content = content;
        this.character = character;
        this.mailHost = mailHost;
        this.accessories = accessories;
        this.isCheckSend = isCheckSend;
    }
    /**
     * 
     * @param subject 主题
     * @param sendMail 发件人
     * @param sendMailPassword 密码
     * @param tos 收件人 
     * @param content 内容
     * @param character  内容字符集
     * @param mailHost SMTP主机
     * @param isCheckSend 是否需要验证
     * @param accessories 附件
     * @param cc 抄送
     */
    public MailBean(String subject, String sendMail, String sendMailPassword,
            List<String> takeMail, String content, String character,
            String mailHost, List<String> accessories, String isCheckSend, List<String> cc) {
        this.subject = subject;
        this.sendMail = sendMail;
        this.sendMailPassword = sendMailPassword;
        this.tos = takeMail;
        this.content = content;
        this.character = character;
        this.mailHost = mailHost;
        this.accessories = accessories;
        this.isCheckSend = isCheckSend;
        this.ccs = cc;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSendMail() {
        return sendMail;
    }
    public void setSendMail(String sendMail) {
        this.sendMail = sendMail;
    }
    public String getSendMailPassword() {
        return sendMailPassword;
    }
    public void setSendMailPassword(String sendMailPassword) {
        this.sendMailPassword = sendMailPassword;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCharacter() {
        return character;
    }
    public void setCharacter(String character) {
        this.character = character;
    }
    public String getMailHost() {
        return mailHost;
    }
    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }
    public List<String> getAccessories() {
        return accessories;
    }
    public void setAccessories(List<String> accessories) {
        this.accessories = accessories;
    }
    public String getCheckSend() {
        return isCheckSend;
    }
    public void setCheckSend(String isCheckSend) {
        this.isCheckSend = isCheckSend;
    }
    public List<String> getTos() {
        return tos;
    }
    public void setTos(List<String> tos) {
        this.tos = tos;
    }
    public List<String> getCcs() {
        return ccs;
    }
    public void setCcs(List<String> ccs) {
        this.ccs = ccs;
    }
    public String getIsCheckSend() {
        return isCheckSend;
    }
    public void setIsCheckSend(String isCheckSend) {
        this.isCheckSend = isCheckSend;
    }
    // 获得所有收件人
    private  Address[] getAllTake() throws ArrayIndexOutOfBoundsException, AddressException{
        return valueOfAddress(tos);
    }
    private Address[] valueOfAddress(List<String> list) throws ArrayIndexOutOfBoundsException, AddressException{
        Address[] takes = new Address[0];
        if(null == list || list.size() == 0)
            throw new ArrayIndexOutOfBoundsException("list is null");
        takes = new Address[list.size()];
        for(int i = 0; i < list.size();  ++ i) {
            takes[i]  = InternetAddress.parse(list.get(i))[0];
        }
        return takes;
    }
    private Address[] getAllOtherTake() throws ArrayIndexOutOfBoundsException, AddressException {
        if (null == ccs || ccs.size() == 0)
            return new Address[0];    
        else             
            return valueOfAddress(getCcs());
    }
    // 添加所有附件
    private void addBodyPartByMimeMultipart(MimeMultipart mm,
            List<String> filePaths) throws MessagingException,
            ArrayIndexOutOfBoundsException, NullPointerException {
        
        if (null == filePaths || filePaths.size() == 0)
            return;
        if(null == mm)
            throw new NullPointerException("MimeMultipart is null");
        for (int i = 0; i < filePaths.size(); i++) {
            MimeBodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filePaths.get(i));
            DataHandler dh = new DataHandler(fileds);
            bp.setDisposition(Part.ATTACHMENT);
            bp.setFileName(fileds.getName());
            bp.setDataHandler(dh);
            mm.addBodyPart(bp);
        }
    }
    
    public boolean send() throws Exception {
        boolean isSend = false;
        
        // 设置SMTP发送服务器
        Properties props = System.getProperties();
        
        // 设置SMTP的主机
        props.put("mail.smtp.host", mailHost);          
        
        // 需要经过验证
        props.put("mail.smtp.auth", isCheckSend);       
        //Authenticator auth = Authenticator
        
        // 获得邮件会话对象
        Session session = Session.getDefaultInstance(props,null);
        URLName url = new URLName(this.sendMail);
        PasswordAuthentication pw = new PasswordAuthentication(this.sendMail, this.sendMailPassword);
        session.setPasswordAuthentication(url, pw);
        
        // 邮件对象
        MimeMessage mimeMsg = new MimeMessage(session);
        
        MimeMultipart multipart = new MimeMultipart();
        
        // 发送邮件地址
        mimeMsg.setFrom(new InternetAddress(sendMail));
        
        // 收件人
        mimeMsg.setRecipients(Message.RecipientType.TO, getAllTake());
        
        mimeMsg.setSentDate(new Date());
        // 抄送人
        mimeMsg.setRecipients(Message.RecipientType.CC, getAllOtherTake());
        
        // 主题 与字符编码
        mimeMsg.setSubject(getSubject(), getCharacter());
        
        // 设置邮件体格式
        BodyPart bp = new MimeBodyPart();
        //bp.setContent("<meta http-equiv=Content-Type content=text/html; charset="+getCharacter()+">"
        //                + content, "text/html;charset="+getCharacter());
        
        bp.setText(content);
        
        log.info(bp.getContent().toString());
        multipart.addBodyPart(bp);
        
        
        // 添加附件
        addBodyPartByMimeMultipart(multipart, getAccessories());
        
        isSend = process(session, mimeMsg, multipart, props);
        
        return isSend;
    }
    
    private boolean process(Session session, MimeMessage mimeMsg, MimeMultipart multipart, Properties props) throws MessagingException {
        boolean isSuccessfully = false;
        mimeMsg.setContent(multipart);
        mimeMsg.saveChanges();
        Session mailSession = Session.getInstance(props, null);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect((String) props.get("mail.smtp.host"), getSendMail(),
                getSendMailPassword());
        transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
        log.info("Mail Successfully Sended!");
        transport.close();
        isSuccessfully = true;
        return isSuccessfully;
    }

}