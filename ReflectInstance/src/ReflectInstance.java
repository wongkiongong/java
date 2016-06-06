import java.lang.Class;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field; 
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.Integer;

public class ReflectInstance {
	
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException{
	
		@SuppressWarnings("rawtypes")
		Class a = Class.forName("java.lang.Integer");
//		Object object = a.newInstance();
		Field[] fs = a.getDeclaredFields();
		
		StringBuffer sb = new StringBuffer();
		sb.append(Modifier.toString(a.getModifiers()) + " class " + a.getSimpleName() +"{\n");
		for(Field field:fs){  
	        sb.append("\t");//空格  
	        sb.append(Modifier.toString(field.getModifiers())+" ");//获得属性的修饰符，例如public，static等等  
	        sb.append(field.getType().getSimpleName() + " ");//属性的类型的名字  
	        sb.append(field.getName()+";\n");//属性的名字+回车  
	    }	
	    sb.append("}");  
	
	    System.out.println(sb); 
		
	    
	    // 获取类
		Class b = JustAClass.class;		
		Class d = b.getClass();
		
		JustAClass jac = new JustAClass("JustAClass");			
//		<span style="white-space:pre">  </span>//以前的方式：  	     
	    jac.setName("oldname"); //set 
	    System.out.println(jac.getName()); //get 
	      
	    // new method
	    //获取类  
	    Class cls = Class.forName("JustAClass");  
	    //获取id属性  
	    Field idF = cls.getDeclaredField("name");  
	    //实例化这个类赋给o  
	    Object o = cls.newInstance();
	    //打破封装  
	    idF.setAccessible(true); //使用反射机制可以打破封装性，导致了java对象的属性不安全。  
	    //给o对象的id属性赋值"110"  
	    idF.set(o, "newname"); //set  
	    //get  
	    System.out.println(idF.get(o));  
	}
}
