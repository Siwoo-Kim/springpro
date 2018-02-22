package com.siwoo.springpro.aop.pointcut;

import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;

public class ControlFlowDemo {

    public static class SimpleBeforeAdvice implements MethodBeforeAdvice{

        @Override
        public void before(Method method, Object[] objects, Object o) throws Throwable {
            System.out.println("Before method: "+method);
        }

    }

    public static class TestBean{
        public void foo(){
            System.out.println("foo()");
        }
    }

    public static void main(String[] args) {
        ControlFlowDemo ex = new ControlFlowDemo();
        ex.run();
    }

    private void run() {
        TestBean testBean = new TestBean();
        Pointcut pc = new ControlFlowPointcut(ControlFlowDemo.class,"test");
        Advisor advisor = new DefaultPointcutAdvisor(pc,new SimpleBeforeAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(testBean);
        pf.addAdvisor(advisor);

        TestBean proxy = (TestBean) pf.getProxy();
        System.out.println("\tTrying normal invoke");
        proxy.foo();
        System.out.println("\n\tTrying under ControlFlowDemo.test()");
        test(proxy);
        myBoo(proxy);
    }

    private int myBoo(TestBean proxy) {
        proxy.foo(); return 1;
    }

    private void test(TestBean proxy) {
        proxy.foo();
    }
}























