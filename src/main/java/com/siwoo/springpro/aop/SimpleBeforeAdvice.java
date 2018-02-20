package com.siwoo.springpro.aop;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

public class SimpleBeforeAdvice implements MethodBeforeAdvice{

    public interface Singer {
        void song();
    }
    public static class Guitarist implements Singer{
        private static final String lyric = "You're gonna live forever in me";
        public void song() {
            System.out.println(lyric);
        }
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("Before '"+method.getName()+"', tune guitar.");
    }

    public static void main(String[] args) {
        Guitarist johnMAYER = new Guitarist();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(johnMAYER);
        proxyFactory.addAdvice(new SimpleBeforeAdvice());

        Guitarist proxy = (Guitarist) proxyFactory.getProxy();

        proxy.song();
    }
}
