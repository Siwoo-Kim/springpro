package com.siwoo.springpro.aop.pointcut;

import com.siwoo.springpro.aop.SimpleBeforeAdvice;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

public class RegexpPointcutDemo {

    private static class Guitarist implements SimpleBeforeAdvice.Singer{

        @Override
        public void song() {
            System.out.println("Just keep me where the light is");
        }

        public void song2() {
            System.out.println("Just keep me where the light is");
        }

        public void rest(){
            System.out.println("zzz");
        }
    }

    @Test
    public void test(){
        Guitarist guitarist = new Guitarist();

        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("com.siwoo.*song$");
        Advisor advisor = new DefaultPointcutAdvisor(pointcut,new StaticPointcutDemo.SimpleAdivce());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(guitarist);

        Guitarist proxy = (Guitarist) proxyFactory.getProxy();
        proxy.song();
        proxy.song2();
        proxy.rest();
    }
}
