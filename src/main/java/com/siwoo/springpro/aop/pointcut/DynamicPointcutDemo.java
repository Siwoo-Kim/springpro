package com.siwoo.springpro.aop.pointcut;

import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

public class DynamicPointcutDemo {

    public static class SampleBean{
        public void foo(int x){
            System.out.println("Invoked foo() with: "+x);
        }
        public void bar(){
            System.out.println("Invoked bar()");
        }
    }

    public static class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut{

        @Override
        public ClassFilter getClassFilter() {
            return aClass -> aClass == SampleBean.class;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            System.out.println("Static check for "+method.getName());
            return ( "foo".equals(method.getName()) );
        }

        @Override
        public boolean matches(Method method, Class<?> aClass, Object... args) {
            System.out.println("Dynamic check for "+method.getName());
            int arg = ((Integer)args[0]).intValue();
            return ( arg != 100 );
        }
    }

    @Test
    public void testing(){
        SampleBean bean = new SampleBean();

        Advisor advisor = new DefaultPointcutAdvisor(new SimpleDynamicPointcut(),new StaticPointcutDemo.SimpleAdivce());

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvisor(advisor);
        factory.setTarget(bean);
        SampleBean proxy = (SampleBean) factory.getProxy();

        proxy.foo(1);
        proxy.foo(10);
        proxy.foo(100);

        proxy.bar();
        proxy.bar();
        proxy.bar();
    }
}
