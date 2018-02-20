package com.siwoo.springpro.aop;

import lombok.extern.java.Log;
import org.junit.Test;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

@Log
public class SimpleAfterReturningAdivce implements AfterReturningAdvice{

    @Test
    public void test(){
        SimpleBeforeAdvice.Guitarist guitarist = new SimpleBeforeAdvice.Guitarist();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(guitarist);
        proxyFactory.addAdvice(new SimpleAfterReturningAdivce());

        SimpleBeforeAdvice.Guitarist proxy = (SimpleBeforeAdvice.Guitarist) proxyFactory.getProxy();
        proxy.song();
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.warning("After '"+method.getName()+"' put down guitar.");
    }
}
