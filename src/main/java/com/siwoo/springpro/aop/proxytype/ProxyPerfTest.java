package com.siwoo.springpro.aop.proxytype;

import org.springframework.aop.Advisor;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class ProxyPerfTest {

    public interface SimpleBean{
        void advised();
        void unadvised();
    }

    public static class DefaultSimpleBean implements SimpleBean{
        private long dummy = 0;
        @Override
        public void advised() {
            dummy = System.currentTimeMillis();
        }

        @Override
        public void unadvised() {
            dummy = System.currentTimeMillis();
        }
    }

    public static class TestPointcut extends StaticMethodMatcherPointcut{
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            return ("advised".equals(method.getName()));
        }
    }
    public static class NoOpBeforeAdvice implements MethodBeforeAdvice{
        @Override
        public void before(Method method, Object[] objects, Object o) throws Throwable {
            //no-op
        }
    }

    public static void main(String[] args) {
        SimpleBean target = new DefaultSimpleBean();
        Advisor advisor = new DefaultPointcutAdvisor(new TestPointcut(),new NoOpBeforeAdvice());
        
        runCglibTests(advisor,target);
        runCglibFronzenTests(advisor,target);
        runJdkTests(advisor,target);
        
    }

    private static void runJdkTests(Advisor advisor, SimpleBean target) {
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        pf.setInterfaces(SimpleBean.class);

        SimpleBean proxy = (SimpleBean) pf.getProxy();
        System.out.println("Running JDK Tests");
        test(proxy);
    }

    private static void runCglibTests(Advisor advisor, SimpleBean target) {
        ProxyFactory pf = new ProxyFactory();
        pf.setProxyTargetClass(true);
        pf.setTarget(target);
        pf.addAdvisor(advisor);

        SimpleBean proxy = (SimpleBean) pf.getProxy();
        System.out.println("Running CGLIB (Standard) Tests");
        test(proxy);
    }

    private static void runCglibFronzenTests(Advisor advisor, SimpleBean target) {
        ProxyFactory pf = new ProxyFactory();
        pf.setProxyTargetClass(true);
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        pf.setFrozen(true);

        SimpleBean proxy = (SimpleBean) pf.getProxy();
        System.out.println("Running CGLIB (Fronzen) Tests");
        test(proxy);
    }

    private static void test(SimpleBean proxy) {
        long before = 0;
        long after = 0;
        System.out.println("Testing Advised Method");
        before = System.currentTimeMillis();
        for(int x=0;x<500000;x++){
            proxy.advised();
        }
        after = System.currentTimeMillis();
        System.out.println("Took "+(after - before)+" ms");

        System.out.println("Testing Unadvised Method");
        before = System.currentTimeMillis();
        for(int x=0;x<500000;x++){
            proxy.unadvised();
        }
        after = System.currentTimeMillis();
        System.out.println("Took "+(after - before)+" ms");

        System.out.println("Testing equals() Method");
        before = System.currentTimeMillis();
        for(int x=0;x<500000;x++){
            proxy.equals(proxy);
        }
        after = System.currentTimeMillis();
        System.out.println("Took "+(after - before)+" ms");

        Advised advised = (Advised) proxy;
        System.out.println("Testing Advised.getProxyTargetClass() Method");
        before = System.currentTimeMillis();
        for(int x=0;x<500000;x++){
            advised.getTargetClass();
        }
        after = System.currentTimeMillis();
        System.out.println("Took "+(after - before)+" ms");
        System.out.println(">>>\n");
    }
}




































