import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;

//动态角色：动态生成代理类
class BusinessImplProxy implements InvocationHandler
{
    private Object obj;
    BusinessImplProxy() {
    }
    BusinessImplProxy(Object obj) {
        this.obj = obj;
    }
    public Object invoke(Object proxy,Method method,Object[] args) throws Throwable
    {
        Object result = null;
        doBefore();
        result = method.invoke(obj,args);
        doAfter();
        return result;
    }
    public void doBefore(){
        System.out.println("do something before Business Logic");
    }
    public void doAfter(){
        System.out.println("do something after Business Logic");
    }
    public static Object factory(Object obj)
    {
        Class cls = obj.getClass();
        return Proxy.newProxyInstance(cls.getClassLoader(),cls.getInterfaces(),new BusinessImplProxy(obj));
    }
}