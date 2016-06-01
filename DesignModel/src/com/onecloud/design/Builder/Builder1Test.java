package com.onecloud.design.Builder;

public class Builder1Test {
	
	public static void main(String[] args){
		
		Builder1 builder = new Builder1();
		builder.produceMailSender(5);
		builder.produceSmsSender(3);
		
		for(Sender s : builder.list){
			s.Send();
		}
	}
}
