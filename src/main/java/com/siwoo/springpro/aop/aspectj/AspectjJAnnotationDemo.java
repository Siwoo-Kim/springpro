package com.siwoo.springpro.aop.aspectj;

import com.siwoo.springpro.aop.SimpleBeforeAdvice;
import com.siwoo.springpro.common.GrammyGuitarist;
import com.siwoo.springpro.common.NewDocumentarist;
import org.springframework.context.annotation.*;

public class AspectjJAnnotationDemo {

    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class AspectjConfig{

        @Bean
        NewDocumentarist documentarist(){
            NewDocumentarist documentarist = new NewDocumentarist();
            documentarist.setDep(johnMayer());
            return documentarist;
        }

        @Bean
        AnnotatedAdvice annotatedAdvice(){
            return new AnnotatedAdvice();
        }

        @Bean(name = "johnMayer")
        GrammyGuitarist johnMayer(){
            return new GrammyGuitarist();
        }

    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AspectjConfig.class);
        NewDocumentarist documentarist = context.getBean(NewDocumentarist.class);
        documentarist.execute();

    }
}
