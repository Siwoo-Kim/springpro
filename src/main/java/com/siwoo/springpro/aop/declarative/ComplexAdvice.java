package com.siwoo.springpro.aop.declarative;

import com.siwoo.springpro.common.Guitar;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


public class ComplexAdvice {
    public void simpleBeforeAdvice(JoinPoint joinPoint, Guitar value){
        if("Gibson".equals(value.getBrand())){
            System.out.println("Executing: "+joinPoint.getSignature().getDeclaringTypeName()+ " " +
                    joinPoint.getSignature().getName());
        }
    }

    public Object simpleAroundAdvice(ProceedingJoinPoint pjp, Guitar value) throws Throwable {

        if("Gibson".equals(value.getBrand())) {
            System.out.println("Before execution: " + pjp.getSignature().getDeclaringTypeName()
                    + " " + pjp.getSignature().getName() + " argument: " + value.getBrand());

            Object result = pjp.proceed();

            System.out.println("After execution: " + pjp.getSignature().getDeclaringTypeName()
                    + " " + pjp.getSignature().getName() + " argument: " + value.getBrand());

            return result;
        }else{
            Object result = pjp.proceed();
            return result;
        }

    }
}
