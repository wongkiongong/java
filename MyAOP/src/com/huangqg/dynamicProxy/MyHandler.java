package com.huangqg.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

// 代理类
public class MyHandler implements InvocationHandler{
	private Object proxyObj;
	private static Logger log=Logger.getLogger(MyHandler.class);
	
	public Object bind(Object obj){
		this.proxyObj=obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
	}
	
	public Object invoke(Object proxy,Method method,Object[] args) throws Throwable{
		Object result=null;
		try{
			//请在这里插入代码，在方法前调用
			log.info("调用log日志方法"+method.getName());
			result=method.invoke(proxyObj,args); //原方法
			//请在这里插入代码，方法后调用
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
