package com.siwoo.springpro.aop.pointcut;

import com.siwoo.springpro.aop.SimpleBeforeAdvice;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class NamePointcutDemo {

    public static class GrammyGuitarist implements SimpleBeforeAdvice.Singer {

        @Override
        public void song() {
            System.out.println("song: Gravity is working against me\n" +
                    "And gravity wants to bring me down");
        }

        public void song(Guitar guitar){
            System.out.println("Play: "+guitar.play());
        }

        public void rest(){
            System.out.println("zzz");
        }

        public void talk(){
            System.out.println("talk");
        }
    }

    public static class Guitar{
        public String play(){
            return "G C G C Am D7";
        }
    }

    @Test
    public void test(){
        GrammyGuitarist guitarist = new GrammyGuitarist();

        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.addMethodName("song");
        nameMatchMethodPointcut.addMethodName("rest");

        Advisor advisor = new DefaultPointcutAdvisor(nameMatchMethodPointcut,new StaticPointcutDemo.SimpleAdivce());
        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(guitarist);

        GrammyGuitarist proxy = (GrammyGuitarist) pf.getProxy();
        proxy.song();
        proxy.song(new Guitar());
        proxy.rest();
        proxy.talk();
    }
}



















