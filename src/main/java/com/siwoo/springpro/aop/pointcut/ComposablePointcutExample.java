package com.siwoo.springpro.aop.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import static com.siwoo.springpro.aop.ProxyUtil.getProxy;

import java.lang.reflect.Method;


public class ComposablePointcutExample {

    private static class SingMethodMatcher extends StaticMethodMatcher{
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            return method.getName().startsWith("so");
        }
    }
    private static class TalkMethodMatcher extends StaticMethodMatcher{
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            return "talk".equals(method.getName());
        }
    }
    private static class RestMethodMatcher extends StaticMethodMatcher{
        @Override
        public boolean matches(Method method, Class<?> aClass) {
            return method.getName().endsWith("st");
        }
    }

    public static void main(String[] args) throws Exception {
        NamePointcutDemo.GrammyGuitarist grammyGuitarist = new NamePointcutDemo.GrammyGuitarist();

        ComposablePointcut pc = new ComposablePointcut(ClassFilter.TRUE,new SingMethodMatcher());
        System.out.println("Text 1 >>");
        NamePointcutDemo.GrammyGuitarist proxy = getProxy(pc, StaticPointcutDemo.SimpleAdivce.class,grammyGuitarist);
        testInvoke(proxy);
        System.out.println();

        System.out.println("Text 2 >>");
        pc.union(new TalkMethodMatcher());
       proxy = getProxy(pc, StaticPointcutDemo.SimpleAdivce.class,grammyGuitarist);
        testInvoke(proxy);
        System.out.println();

        System.out.println("Text 3 >>");
        pc.intersection(new RestMethodMatcher());
        proxy = getProxy(pc, StaticPointcutDemo.SimpleAdivce.class,grammyGuitarist);
        testInvoke(proxy);
        System.out.println();

    }

    private static void testInvoke(NamePointcutDemo.GrammyGuitarist proxy) {
        proxy.song();
        proxy.song(new NamePointcutDemo.Guitar());
        proxy.talk();
        proxy.rest();
    }
}
