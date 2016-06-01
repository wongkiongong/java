package com.onecloud.design.Proxy;
/**
 * @author huangqg
 * 1、需要扩展一个类的功能。
 * 2、动态的为一个对象增加功能，而且还能动态撤销。（继承不能做到这一点，继承的功能是静态的，不能动态增删。）
 * 缺点：产生过多相似的对象，不易排错！
 */

public class ProxyTest {
	public static void main(String[] args){
		Source source = new Source();
		Sourceable decorater = new Proxy(source);
		decorater.method();
	}
}
