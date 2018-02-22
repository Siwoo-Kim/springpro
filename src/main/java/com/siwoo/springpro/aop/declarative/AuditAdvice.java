package com.siwoo.springpro.aop.declarative;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class AuditAdvice {

    public void simpleBeforeAdvice(JoinPoint joinPoint){
        System.out.println("Executing: "+joinPoint.getSignature().getDeclaringTypeName()+" "
                +joinPoint.getSignature().getName());
    }

}
