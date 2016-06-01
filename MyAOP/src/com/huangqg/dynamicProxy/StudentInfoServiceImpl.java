package com.huangqg.dynamicProxy;

public class StudentInfoServiceImpl implements StudentInfoService {
	public void findInfo(String name){
		System.out.println("你目前输入的名字是:"+name);
		}
}
