package com.siwoo.springpro.aop.declarative;

import com.siwoo.springpro.aop.introduction.IsModified;
import com.siwoo.springpro.aop.introduction.IsModifiedAdvisor;
import com.siwoo.springpro.common.Contact;
import com.siwoo.springpro.common.Documentarist;
import com.siwoo.springpro.common.NewDocumentarist;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class ProxyFactoryBeanDemo {

    @Configuration
    public static class AppConfig{

        @Bean
        public Contact guitarist(){
            Contact guitarist = new Contact();
            guitarist.setName("John Mayer");
            return guitarist;
        }

        @Bean
        public Advisor advisor(){
            return new IsModifiedAdvisor();
        }

        @Bean(name = "bean")
        public ProxyFactoryBean bean(){
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(guitarist());
            proxyFactoryBean.setProxyTargetClass(true);
            proxyFactoryBean.addAdvisor(advisor());
            return proxyFactoryBean;
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Contact bean = (Contact) configApplicationContext.getBean("bean");
        IsModified proxyInterface = (IsModified) bean;

        System.out.println("Is Contact?: "+(bean instanceof Contact));
        System.out.println("Is IsModifed? "+(bean instanceof IsModified));
        System.out.println("Has bean modified?: "+proxyInterface.isModified());

        bean.setName("John Mayer");
        System.out.println("Has bean modified?: "+proxyInterface.isModified());

        bean.setName("Eric Clapton");
        System.out.println("Has bean modified?: "+proxyInterface.isModified());

        configApplicationContext.close();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/aop/app-context.xml");
        NewDocumentarist documentarist = (NewDocumentarist) context.getBean("documentarist");
        documentarist.execute();
    }
}
