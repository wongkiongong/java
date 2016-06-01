package com.onecloud.design.Decorater;

public class Decorater implements Sourceable {

	private Source source;

	public Decorater(Source source){
		super();
		this.source = source;
	}
	@Override
	public void method() {
		// TODO Auto-generated method stub
		System.out.println("before decorater");
		source.method();
		System.out.println("after decorater");
	}

}
