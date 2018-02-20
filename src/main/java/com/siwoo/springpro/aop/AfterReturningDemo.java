package com.siwoo.springpro.aop;

import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.stream.IntStream;

import static com.siwoo.springpro.aop.AfterReturningDemo.KeyGenerator.WEAK_KEY;

public class AfterReturningDemo  {


    public static class KeyGenerator{
        protected static final long WEAK_KEY = 0xFFFFFFF0000000L;
        protected static final long STRONG_KEY = 0xACDF03F590AE56L;
        private Random random = new Random();

        public long getKey(){
            int x = random.nextInt(3);

            if(x == 1){
                return WEAK_KEY;
            }
            return STRONG_KEY;
        }
    }

    public static class WeakKeyCheckAdvice implements AfterReturningAdvice{
        @Override
        public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
            if((target instanceof KeyGenerator) && ("getKey".equals(method.getName()))){
                long generatedKey = ((Long)returnValue).longValue();
                if(generatedKey == WEAK_KEY){
                    throw new SecurityException("Key Generator generated a weak key. Try again");
                }
            }
        }
    }

    @Test
    public void test() throws Exception {
        KeyGenerator keyGenerator = ProxyUtil.getProxy(KeyGenerator.class,WeakKeyCheckAdvice.class);

        for(int i=0;i<10;i++){
            try{
                long key = keyGenerator.getKey();
                System.out.println("Key: "+key);
            }catch (SecurityException e){
                System.out.println("Weak Key Generated!");
            }
        }
    }

}














