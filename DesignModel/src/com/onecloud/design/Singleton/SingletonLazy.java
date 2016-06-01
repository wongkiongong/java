package com.onecloud.design.Singleton;

public class SingletonLazy {
	
//	private static Singleton singleton = null;
	
	private SingletonLazy(){		
	}
	
/*	public static Singleton getInstance(){
		
		synchronized(singleton){
			if(singleton == null){
				singleton = new Singleton();
			}
		}
		
		return singleton;
	}*/
	
	private static class SingleFactory{
		private static SingletonLazy singleton = new SingletonLazy();
	}
	
	public static SingletonLazy getInstance(){
		return SingleFactory.singleton;
	}
	
	public Object readResolve(){
		return getInstance();
	} 
}
