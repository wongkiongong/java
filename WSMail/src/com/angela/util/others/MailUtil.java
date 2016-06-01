/**
 * 邮件工具类
 */
package com.angela.util.others;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
/**
 * @Program Name : angelaws.com.angela.util.others.MailUtil.java
 * @Written by : kzw
 * @version : v1.00
 * @Creation Date : 2012-7-17 上午10:40:40
 * @Description :
 * 邮件操作工具类
 * @ModificationHistory 
 * Who             When         What 
 * ------- -------------     ----------------- 
 * kzw    2012-7-17 上午10:40:40         create
 * 
 **/
public class MailUtil {
    private static Logger log = LogManager.getLogger(MailUtil.class);
    
    private static final String CONFIGPATH = "mail.properties";
    private static final String SMTP = "smtp";
    private static final String EMAILREGEX = "emailRegex";
    private static Properties mailConfig;
    
    static {
        reloadPro();
    }
    /**
     * 获取邮箱对应SMTP服务器
         * 通过邮件来判断需要的SMTP主机，这里默认了使用了QQ的SMTP服务器
     * @Eclosing_Method  : getMail2SMTP
     * @author  by       : kzw
     * @creation Date    : 2012-7-17上午11:02:33
     * @param mail
     * @return
     *
     */
    public static String getMail2SMTP(String mail) {
        return (String)getConfigValue(SMTP);
    }
    public static String getConfigValue(String key) {
        if(null == mailConfig)
            reloadPro();
        return (String)mailConfig.get(key);
    }
    /**
     * 重新加载配置文件
     * @Eclosing_Method  : reloadPro
     * @author  by       : kzw
     * @creation Date    : 2012-7-17上午11:57:19
     *
     */
    public static void reloadPro(){
        mailConfig = new Properties(); 
        try {
            mailConfig.load(MailUtil.class.getClassLoader().getResourceAsStream(CONFIGPATH));
        } catch (IOException e) {
            log.error("加载邮件配置错误! e=" + e.getMessage());
        }
    }
    /**
     * 邮箱地址是否正确
     * @Eclosing_Method  : isEmail
     * @author  by       : kzw
     * @creation Date    : 2012-7-17下午12:17:28
     * @param email
     * @return
     *
     */
    public static boolean isEmail(String email) {
        if(StringUtils.isNotBlank(email))
            return email.matches(getConfigValue(EMAILREGEX));
        return false;
    }
}