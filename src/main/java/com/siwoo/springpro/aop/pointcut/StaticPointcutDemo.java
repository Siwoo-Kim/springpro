package com.siwoo.springpro.aop.pointcut;

import com.siwoo.springpro.aop.SimpleBeforeAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

import static com.siwoo.springpro.aop.ProxyUtil.getProxy;

public class StaticPointcutDemo {

    public static class GoodGuitarist implements SimpleBeforeAdvice.Singer{

        @Override
        public void song() {
            System.out.println("Who says I can't be free \n" +
                    "From all of the things that I used to be");
        }
    }
    public static class GreatGuitarist implements SimpleBeforeAdvice.Singer{

        @Override
        public void song() {
            System.out.println("I shot the sheriff, \n" +
                    "But I did not shoot the deputy");
        }
    }
    public static class SimpleStaticPointcut extends StaticMethodMatcherPointcut{
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            return ("song".equals(method.getName()));
        }

        @Override
        public ClassFilter getClassFilter() {
            return aClass -> (aClass == GoodGuitarist.class);
            /*return new ClassFilter() {
                @Override
                public boolean matches(Class<?> aClass) {
                    return (aClass == GoodGuitarist.class)
                }
            }*/
        }
    }
    public static class SimpleAdivce implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            System.out.println(">> Invoking "+methodInvocation.getMethod().getName());
            Object result = methodInvocation.proceed();
            System.out.println(">> Done\n");
            return result;
        } //around advice
    }

    @Test
    public void test(){
        GoodGuitarist johnMayer = new GoodGuitarist();
        GreatGuitarist ericClapton = new GreatGuitarist();

        SimpleBeforeAdvice.Singer proxyOne;
        SimpleBeforeAdvice.Singer proxyTwo;

        Pointcut pointcut = new SimpleStaticPointcut();
        Advice advice = new SimpleAdivce();
        Advisor advisor = new DefaultPointcutAdvisor(pointcut,advice);

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(johnMayer);
        proxyOne = (SimpleBeforeAdvice.Singer)pf.getProxy();

        pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(ericClapton);
        proxyTwo = (SimpleBeforeAdvice.Singer)pf.getProxy();

        proxyOne.song();
        proxyTwo.song();

    }

}





















