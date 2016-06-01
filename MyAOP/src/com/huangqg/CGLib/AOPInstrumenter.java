package com.huangqg.CGLib;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AOPInstrumenter implements MethodInterceptor {
	private Logger log=Logger.getLogger(AOPInstrumenter.class);
	private Enhancer enhancer=new Enhancer();
	public Object getInstrumentedClass(Class clz){
		enhancer.setSuperclass(clz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
		
	@Override
	public Object intercept(Object o,Method method,Object[] args,MethodProxy proxy) throws Throwable {
		// TODO Auto-generated method stub
		log.info("调用日志方法"+method.getName());
		Object result=proxy.invokeSuper(o,args);
		return result;
	}

}
