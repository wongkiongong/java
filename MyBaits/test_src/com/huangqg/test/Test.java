package com.huangqg.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.huangqg.inter.IUserOperation;
import com.huangqg.mybatis.model.User;

public class Test {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static{
        try{
            reader = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
   
    public static void main(String[] args) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
//        User user = (User) session.selectOne("com.huangqg.mybatis.models.UserMapper.selectUserByID", 1);
          IUserOperation userOperation = session.getMapper(IUserOperation.class);
          
          // query one record
          User user = userOperation.selectUserByID(1);

          System.out.println(user.getUserAddress());
          System.out.println(user.getUserName());
          System.out.println(user.getUserAge());
          
          // query list
          List<User> users = userOperation.selectUserName("su%");
          for(User u : users){
        	  System.out.println("name:" + u.getUserName() + "; Age:" + u.getUserAge()
        	  + "; Address:" + u.getUserAddress()); 
          }
          
          // add record
//          User usr = new User();
//          usr.setUserName("huangqg");
//          usr.setUserAge(32);
//          usr.setUserAddress("poly garden");
//          userOperation.addUser(usr);
//          session.commit();
          
          // update record
          user.setUserAge(23);
          userOperation.updateUser(user);
          session.commit();
          
          // delete record
          userOperation.deleteUser(4);
          session.commit();
          
        } finally {
        session.close();
        }
    }
}
