package com.siwoo.springpro.ch3.lookup_method_injection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

public class LookupDemo {
    public static void main(String[] args) {
        //GenericXmlApplicationContext context = new GenericXmlApplicationContext(new ClassPathResource("META-INF/spring/ch3/app-context-xml.xml"));
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LookupConfig.class);

        DemoBean abstractLookupBean = (DemoBean) context.getBean("abstractLookupBean",AbstractLookupDemoBean.class);
        DemoBean standardLookupBean =(DemoBean) context.getBean("standardLookupBean");
        displayInfo("abstractLookupBean",abstractLookupBean);
        displayInfo("standardLookupBean",standardLookupBean);
    }

    @Configuration
    @ComponentScan(basePackages = {"com.siwoo.springpro.ch3.lookup_method_injection"})
    public static class LookupConfig{}

    private static void displayInfo(String beanName, DemoBean bean) {
        Singer singer1 = bean.getMySinger();
        Singer singer2 = bean.getMySinger();

        System.out.println(""+beanName + ": Singer Instances the Same? "+ (singer1 == singer2));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("lookupDemo");;
        for(int x=0; x< 100000; x++){
            Singer singer = bean.getMySinger();
            singer.sing();
        }

        stopWatch.stop();
        System.out.println("1000000 gets took "+ stopWatch.getTotalTimeMillis()+" ms");
    }
}
