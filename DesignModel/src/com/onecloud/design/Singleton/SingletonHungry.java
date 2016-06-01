package com.onecloud.design.Singleton;
/* hungryton singleton
 * Thread-safe
 * 1天生就是线程安全的，可以直接用于多线程而不会出现问题;
 * 2在类创建的同时就实例化一个静态对象出来，不管之后会不会使用这个单例，
 * 都会占据一定的内存，但是相应的，在第一次调用时速度也会更快，因为其资源已经初始化完成
 */

public class SingletonHungry {

	public SingletonHungry() {
	}
	
	private static final SingletonHungry single = new SingletonHungry();
	
	public static SingletonHungry getInstance(){
		return single;
	}
}
