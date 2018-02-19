package com.siwoo.springpro.ch3;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class InjectRef {

    private Oracle oracle;

    public void setOracle(Oracle oracle){
        this.oracle = oracle;
    }

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(new ClassPathResource("META-INF/spring/ch3/app-context-xml.xml"));
        context.refresh();

        InjectRef injectRef = context.getBean("injectRef",InjectRef.class);
        System.out.println(injectRef);

        context.close();
    }

    public String toString(){
        return oracle.defineMeaningOfLife();
    }

}
