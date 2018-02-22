package com.siwoo.springpro.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class ProxyUtil {

    public static <T,A extends Advice> T getProxy(Class<T> targetClass, Class<A> adviceName) throws Exception{
        T target = (T) Class.forName(targetClass.getName()).newInstance();
        A advice = (A) Class.forName(adviceName.getName()).newInstance();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(advice);
        return (T) proxyFactory.getProxy();
    }


    public static <T,A extends Advice> T getProxy(Pointcut pointcut, Class<A> adviceName, Object target) throws Exception{
        Advisor advisor = new DefaultPointcutAdvisor(pointcut, (A) Class.forName(adviceName.getName()).newInstance());
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor);
        return (T) proxyFactory.getProxy();
    }

}
