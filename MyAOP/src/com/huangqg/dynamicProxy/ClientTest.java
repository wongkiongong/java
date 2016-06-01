package com.huangqg.dynamicProxy;

public class ClientTest {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		StudentInfoService studenInfo = (StudentInfoService)AOPFactory.getAOPProxyedObject("com.huangqg.dynamicPorxy.StudentInfoServiceImpl");
		studenInfo.findInfo("huangqg");
	}
}
