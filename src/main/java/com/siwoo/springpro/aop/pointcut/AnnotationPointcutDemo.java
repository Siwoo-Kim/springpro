package com.siwoo.springpro.aop.pointcut;

import com.siwoo.springpro.aop.SimpleBeforeAdvice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import javax.xml.stream.events.EndElement;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AnnotationPointcutDemo {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE /*class*/,ElementType.METHOD})
    @interface AdviceRequired{}

    public static class Guitarist implements SimpleBeforeAdvice.Singer{

        @Override
        public void song() {
            System.out.println("Dream of ways to throw it all away");
        }

        @AdviceRequired
        public void song(NamePointcutDemo.Guitar guitar){
            System.out.println("play: "+guitar.play());
        }

        public void rest(){
            System.out.println("zzz");
        }
    }

    public static void main(String[] args) {
        Guitarist guitarist = new Guitarist();

        AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(AdviceRequired.class);
        Advisor advisor = new DefaultPointcutAdvisor(pointcut,new StaticPointcutDemo.SimpleAdivce());

        ProxyFactory fc = new ProxyFactory();
        fc.addAdvisor(advisor);
        fc.setTarget(guitarist);
        Guitarist proxy = (Guitarist) fc.getProxy();

        proxy.song();
        proxy.song(new NamePointcutDemo.Guitar());
        proxy.rest();
    }
}
