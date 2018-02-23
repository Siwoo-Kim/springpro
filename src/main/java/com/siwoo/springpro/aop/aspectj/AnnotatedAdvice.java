package com.siwoo.springpro.aop.aspectj;

import com.siwoo.springpro.common.Guitar;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotatedAdvice {

    @Pointcut("execution(* com..sing*(com..Guitar)) && args(value)")
    public void signExecution(Guitar value){}

    @Pointcut("bean(john*)")
    public void isJohn(){}

    @Before("signExecution(value) && isJohn()")
    public void simpleBeforeAdvice(JoinPoint jp,Guitar value){
        if("Gibson".equals(value.getBrand())){
            System.out.println("Executing: "+jp.getSignature().getDeclaringTypeName()
                    +" "+jp.getSignature().getName()+" argument: "+value.getBrand());
        }
    }

    @Around("signExecution(value) && isJohn()")
    public Object simpleAroundAdvice(ProceedingJoinPoint pjp, Guitar value) throws Throwable {

        System.out.println("Before execution: "+pjp.getSignature().getDeclaringTypeName()
        +" "+pjp.getSignature().getName()+" argument: "+value.getBrand());

        Object result = pjp.proceed();

        System.out.println("After execution: "+pjp.getSignature().getDeclaringTypeName()
                +" "+pjp.getSignature().getName()+" argument: "+value.getBrand());

        return result;
    }

}
