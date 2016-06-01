package com.huangqg.CGLib;

public class AOPTest {
	public static void main(String[] args){
		AOPInstrumenter instrumenter=new AOPInstrumenter();
		StudentInfoServiceImpl studentInfo=(StudentInfoServiceImpl)instrumenter.getInstrumentedClass(StudentInfoServiceImpl.class);
		studentInfo.findInfo("huangqg");
	}
}
