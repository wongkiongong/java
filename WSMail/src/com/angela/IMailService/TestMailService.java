/**
 * 单元测试
 */
package com.angela.IMailService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.angela.server.IMailService;
import com.angela.server.impl.MailServiceImpl;
/**
 * @Program Name : angelaws.com.angela.IMailService.TestMailService.java
 * @Written by : kzw
 * @version : v1.00
 * @Creation Date : 2012-7-18 上午09:07:25
 * @Description :
 * 
 * @ModificationHistory 
 * Who             When         What 
 * ------- -------------     ----------------- 
 * kzw    2012-7-18 上午09:07:25         create
 * 
 **/
public class TestMailService {
    IMailService service;
    List<String> transmitMails;
    List<String> takeMail;
    List<String> multiparts;
    @Before
    public void init(){
          service = new MailServiceImpl();
          transmitMails = new ArrayList<String>();
          takeMail = new ArrayList<String>();
          multiparts = new ArrayList<String>();
          takeMail.add("100086@qq.com");
    }
    
    @Test
    public void testThreadPool() throws InterruptedException{
        //  策略 1 同时提交1000份请求
        int size = 1000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) 
            service.sendMail(null, null, transmitMails, takeMail, "sub"+ System.currentTimeMillis(),
                    "content"+System.currentTimeMillis(), multiparts, "预订取消","1872", "测试");    
        long end = System.currentTimeMillis();
        Thread.sleep(5000000);
        System.out.println("邮件数量:" + size +", 总耗时:" + (end - start));
    }
    @Test
    public void testMailSend() throws InterruptedException{
        String sendMailAddr = "kuangzhongwei@1872.net";
        String sendMailPwd = "XXX";
        List<String> transmitMails = new ArrayList<String>();
        //transmitMails.add("kuangzhongwei@gmail.com");
        
        List<String> takeMail = new ArrayList<String>();
        takeMail.add("346554535@qq.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subject = sdf.format(new Date()) + " subject";
        String content = sdf.format(new Date()) + "content 中文内容"; 
        
        // HTML代码 注意编码
        /*File f = new File("G:\a.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        System.out.println(new InputStreamReader(new FileInputStream(f)).getEncoding());
        String fileContent = null;
        while(null != (fileContent = in.readLine())) {
            content += fileContent;
        }
        in.close();
        */
        List<String> multiparts = new ArrayList<String>();
        //multiparts.add("G:/a.txt");
        
        new MailServiceImpl().sendMail(sendMailAddr, sendMailPwd, transmitMails, takeMail, subject, content, multiparts, "02","1872", "测试");
        Thread.sleep(200000);
    }
}