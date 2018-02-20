package com.siwoo.springpro.aop;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

import static com.siwoo.springpro.aop.ProxyUtil.getProxy;

public class SimpleThrowsAdvice implements ThrowsAdvice{

    public static class ErrorBean{
        public void errorProneMethod() throws Exception {
            throw new Exception("Generic Exception");
        }

        public void otherErrorProneMethod() throws IllegalStateException{
            throw new IllegalArgumentException("IllegalArgument exception");
        }
    }

    public void afterThrowing(Exception e) throws Throwable{
        System.out.println("***");
        System.out.println("Generic Exception Capture");
        System.out.println("Caught: "+e.getClass().getName());
        System.out.println("***\n");
        throw new AppGenericException("Hi");
    }

    public void afterThrowing(Method method,Object args, Object target,IllegalArgumentException e) throws Throwable{
        System.out.println("***");
        System.out.println("IllegalArgumentException Capture");
        System.out.println("Caught: "+e.getClass().getName());
        System.out.println("***\n");
        throw new AppIllegalArgumentException("App illegal argument exception!");
    }

    public static class AppIllegalArgumentException extends RuntimeException{
        public AppIllegalArgumentException(String message) {
            super(message);
        }
    }

    public static class AppGenericException extends RuntimeException{
        public AppGenericException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ErrorBean errorBean = getProxy(ErrorBean.class,SimpleThrowsAdvice.class);

        //
        try {
            errorBean.otherErrorProneMethod();
        }catch (AppIllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
