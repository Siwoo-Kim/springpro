package com.siwoo.springpro.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyUtil {

    public static <T,A extends Advice> T getProxy(Class<T> targetClass, Class<A> adviceName) throws Exception{
        T target = (T) Class.forName(targetClass.getName()).newInstance();
        A advice = (A) Class.forName(adviceName.getName()).newInstance();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(advice);
        return (T) proxyFactory.getProxy();
    }

}
