package com.onecloud.design.Adapter;

public class ApdaterTest {

	public static void main(String[] args){
		
		Targetable target = new Apdater();
		target.method1();
		target.method2();
		
		Source source = new Source();
		Targetable wrapper = new Wrapper(source);
		wrapper.method1();
		wrapper.method2();
		
		SourceSub1 sub1 = new SourceSub1();
		sub1.method1();
		sub1.method2();
		SourceSub2 sub2 = new SourceSub2();
		sub2.method1();
		sub2.method2();
	}

}
