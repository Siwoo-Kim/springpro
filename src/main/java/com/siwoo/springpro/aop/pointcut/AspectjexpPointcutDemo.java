package com.siwoo.springpro.aop.pointcut;

import com.siwoo.springpro.aop.SimpleBeforeAdvice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AspectjexpPointcutDemo {

    public static void main(String[] args) {
        SimpleBeforeAdvice.Guitarist guitarist = new SimpleBeforeAdvice.Guitarist();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* *..song*(..))");
        Advisor advisor = new DefaultPointcutAdvisor(pointcut,new StaticPointcutDemo.SimpleAdivce());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(guitarist);
        proxyFactory.addAdvisor(advisor);

        SimpleBeforeAdvice.Guitarist proxy = (SimpleBeforeAdvice.Guitarist) proxyFactory.getProxy();
        proxy.song();
        proxy.rest();

    }
}
