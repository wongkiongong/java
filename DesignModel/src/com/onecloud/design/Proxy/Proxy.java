package com.onecloud.design.Proxy;

public class Proxy implements Sourceable {

	private Source source;

	public Proxy(Source source){
		super();
		this.source = source;
	}
	@Override
	public void method() {
		// TODO Auto-generated method stub
		before();
		source.method();
		after();
	}
	
	public void before(){
		System.out.println("before decorater");
	}
	
	public void after(){
		System.out.println("after decorater");
	}
}
