package com.siwoo.springpro.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public class HelloWorldAop {

    public static class Agent{
        public void speak(){
            System.out.printf("Bond");
        }
    }

    public static class AgentDecorator implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            System.out.printf("Jams ");

            Object retVal = methodInvocation.proceed();
            System.out.printf("!");
            return retVal;
        }
    }

    public static void main(String[] args) {
        Agent target = new Agent();
        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.addAdvice(new AgentDecorator());  //weaving
        proxyFactory.setTarget(target);

        Agent proxy = (Agent) proxyFactory.getProxy();

        target.speak();
        System.out.println();
        proxy.speak();
    }
}
