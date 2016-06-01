/**
  * 邮件接口实现类
  */
 package com.angela.server.impl;
 
 import java.math.BigDecimal;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Date;
 import java.util.List;
 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.LinkedBlockingQueue;
 import java.util.concurrent.ThreadPoolExecutor;
 import java.util.concurrent.TimeUnit;
 
 import org.apache.commons.lang.StringUtils;
 import org.apache.log4j.LogManager;
 import org.apache.log4j.Logger;
 
 import com.angela.bean.MailBean;
 import com.angela.common.db.DbUtilsTemplate;
 import com.angela.server.IMailService;
 import com.angela.util.others.MailUtil;
 
 /**
  * @Program Name : angelaws.com.angela.server.impl.MailServiceImpl.java
  * @Written by : kzw
  * @version : v1.00
  * @Creation Date : 2012-7-17 上午10:10:32
  * @Description : 邮件接口实现
  * @ModificationHistory Who When What ------- ------------- -----------------
  *                      kzw 2012-7-17 上午10:10:32 create
  * 
  **/
 public class MailServiceImpl implements IMailService {
     private static Logger log = LogManager.getLogger(MailServiceImpl.class);
     
     /* pool config */
     private static final int COREPOOLSIZE = 10; 
     private static final int MAXIMUMPOOLSIZE = 20; //最大连接数
     
     private static ThreadPoolExecutor pool;
     private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
     
     static {
         /** 
          *  使用JDK中的线程池，线程执行采用无边界策略
          */
         pool = new ThreadPoolExecutor(COREPOOLSIZE, MAXIMUMPOOLSIZE, 2, TimeUnit.MINUTES, queue); 
     }
     @Override
     public void sendMail(String sendMailAddr, String sendMailPwd,
             List<String> ccs, List<String> tos, String subject,
             String content, List<String> accessories, String mailType, String operMan, String memo) {
        MailThread mail = new MailThread();
        mail.init(sendMailAddr, sendMailPwd, ccs, tos, subject, content, accessories, mailType, operMan, memo);
        pool.execute(mail); 
     }
     /**
      * 
      * @Program Name : angelaws.com.angela.server.impl.MailServiceImpl.java
      * @Written by : kzw
      * @version : v1.00
      * @Creation Date : 2012-7-18 下午04:14:23
      * @Description :
      * 发送邮件线程
      * @ModificationHistory 
      * Who             When         What 
      * ------- -------------     ----------------- 
      * kzw    2012-7-18 下午04:14:23         create
      * 
      *
      */
     class MailThread implements Runnable {
         private Logger logger = LogManager.getLogger(MailThread.class);
         
         private List<String> ccs;
         private List<String> tos;
         private List<String> accessories;
         
         private String sendMailAddr;
         private String sendMailPwd;
         private String subject;
         private String content;
         private String mailType;
         private String operMan;
         private String memo;
         
         private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         /**
          *  初始化线程数据
          * @Eclosing_Method  : init
          * @author  by       : kzw
          * @creation Date    : 2012-7-17下午05:25:48
          * @param sendMailAddr 
          * @param sendMailPwd
          * @param ccs 抄送号码
          * @param tos 收件人
          * @param subject 主题
          * @param content 内容
          * @param accessories 附件
          *
          */
         public void init(String sendMailAddr, String sendMailPwd,
                 List<String> ccs, List<String> tos, String subject,
                 String content, List<String> accessories, String mailType, String operMan, String memo){
             this.sendMailAddr = sendMailAddr;
             this.sendMailPwd = sendMailPwd;
             this.ccs = ccs;
             this.tos = tos;
             this.subject = subject;
             this.content = content;
             this.accessories = accessories;
             this.mailType = mailType;
             this.operMan = operMan;
             this.memo = memo;
         }
         @Override
         public void run() {
             logger.info("发送邮件开始" + sdf.format(new Date()));
             int id = 0;
             String flag = "0";  //待发送
             String msg = "";      //发送信息
             boolean res = false;// 发送结果
             try {
                 id = getSeqNo();
                 ccs.addAll(queryCCsByMailType(mailType)); // 添加需要抄送的邮箱地址
                 // 使用默认的邮箱
                 if(StringUtils.isBlank(sendMailAddr)) {
                     sendMailAddr = MailUtil.getConfigValue(DEFAULT_SEND);
                     sendMailPwd = MailUtil.getConfigValue(DEFAULT_SEND_PWD);
                 }
                 
                 // 插入待发送邮件日志
                 saveLog(sendMailAddr, ccs, tos, subject, content, accessories, mailType, operMan, memo, id);
                 
                 // 校验数据, 去掉错误邮箱地址
                 if(!validate(sendMailAddr, sendMailPwd, ccs, tos, subject, content, accessories)) {
                     logger.error("校验邮件数据失败");
                     return;
                 }
                 // 获取邮件对象
                 MailBean mail = getMailInstance(sendMailAddr, sendMailPwd, ccs, tos, subject, content, accessories);
                 
                 // 发送邮件
                 res = mail.send();
                 flag = res ? "1" : "2";
                 if(!res) msg = "未知错误,发送失败";
                 
                 // 更新发送记录
                 modifyLog(msg, flag, id);
                 
             } catch (Exception e) {
                 e.printStackTrace();
                 logger.error(e.getMessage(), e);
                 modifyLog(e.getMessage(), "2", id);
             }
             logger.info("发送邮件结束" + sdf.format(new Date()));    
         }
         
         /**
          * 获取邮件对象实体
          * @Eclosing_Method  : getInstance
          * @author  by       : kzw
          * @creation Date    : 2012-7-17下午03:16:02
          * @param sendMailAddr 发件人
          * @param sendMailPwd 发件人密码
          * @param ccs 抄送
          * @param tos 收件人
          * @param subject 主题
          * @param content 内容
          * @param accessories 附件
          * @return
          *
          */
         private MailBean getMailInstance(String sendMailAddr, String sendMailPwd,
                 List<String> ccs, List<String> tos, String subject,
                 String content, List<String> accessories){
             // 获取SMTP地址
             String mailHost = MailUtil.getMail2SMTP(sendMailAddr);
             
             // 创建邮件
             MailBean mail = new MailBean(subject, 
                     sendMailAddr, 
                     sendMailPwd,
                     tos,  // 收件人
                     content, 
                     MailUtil.getConfigValue(CHARACTERCONFIG), 
                     mailHost, 
                     accessories,    // 附件
                     MailUtil.getConfigValue(CHECKSEND),
                     ccs  //抄送
             );
             
             return mail;
         }
         
         private boolean validate(String sendMailAddr, String sendMailPwd,
                 List<String> transmitMails, List<String> takeMail, String subject,
                 String content, List<String> multiparts){
             boolean res = true;
             if(!MailUtil.isEmail(sendMailAddr)) res = false;
             // 清除错误号码
             cancelErrorMailAddr(transmitMails); 
             cancelErrorMailAddr(takeMail);
             
             return res;
         }
         
         private void cancelErrorMailAddr(List<String> list) {
             if(null == list || list.isEmpty()) return;
             List<String> back = new ArrayList<String>(list);
             for (String email : back) {
                 if(!MailUtil.isEmail(email)) {
                     list.remove(email);
                     log.info("移除错误邮件:" + email);
                 }
             }
         }
 
         private int getSeqNo() {
             String seqSql = "select seq_mail.nextval from dual";
             BigDecimal b =  (BigDecimal) new DbUtilsTemplate().findBy(seqSql, 1);
             return b.intValue();
         }
         
         private boolean saveLog(String sendMail, List<String> ccs, List<String> tos, String subject,
                 String content, List<String> accessories, String mailType, String operman, String memo, int id) {
             
             StringBuffer sql = new StringBuffer(); 
             sql.append(" INSERT INTO mail_send_detail ");
             sql.append(" (ID, sendmail, senddate, tos, ccs, subject, CONTEXT, status, ");
             sql.append(" mailtype, accessories, operman, operdate,opermemo) values(?, ?, sysdate, ?, ?, ?, ?, '0', ?, ?, ?, sysdate, ?)");
             
             String[] params = {
                     String.valueOf(id),
                     sendMail, 
                     Arrays.toString(ccs.toArray()).replace("[", "").replace("]", ""),
                     Arrays.toString(tos.toArray()).replace("[", "").replace("]", ""), 
                     subject,
                     content,
                     mailType,
                     Arrays.toString(accessories.toArray()).replace("[", "").replace("]", ""),
                     operman,
                     memo
             };
             
             return (1 == new DbUtilsTemplate().update(sql.toString(), params)); 
         }
         
         private boolean modifyLog(String memo, String flag, int id){
             String sql ="update mail_send_detail set status=?, memo = ? where id = ?";
             return (1 == new DbUtilsTemplate().update(sql, 
                     new String[]{flag, memo, String.valueOf(id)}));
         }
         // 查询邮件类型下， 需要抄送的
         private List<String> queryCCsByMailType(String mailType) {
             List<String> list = new ArrayList<String>();
             String sql = "select ccs from dm_mail_type where dm01=?";
             String emails =  (String) new DbUtilsTemplate().findBy(sql, 1, mailType);
             if(StringUtils.isNotBlank(emails)) {
                  list = Arrays.asList(emails.split(";"));
             }
             return list;
         }
     }
 }