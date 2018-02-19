package com.siwoo.springpro.ch3.bean_name_resolution;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Map;

public class BeanNamingTest {

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("META-INF/spring/ch3/app-context-xml.xml");

        Map<String,String> beans = context.getBeansOfType(String.class);
        beans.entrySet().stream().forEach(b -> System.out.println(b.getKey() + " "+b.getValue()));
    }
}
