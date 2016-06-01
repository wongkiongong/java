package com.onecloud.design.Factory;

public class FactoryTest {
	
	public static void main(String[] args){
		Provider mailProvider = new MailFactory();
		Sender mailSender = mailProvider.produce();
		mailSender.Send();
		
		Provider smsProvider = new SmsFactory();
		Sender smsSender = smsProvider.produce();
		smsSender.Send();
	}
}
