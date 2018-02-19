package com.siwoo.springpro.ch3.bean_scope;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("nonSingleton")
@Scope("prototype")
public class Singer {
    private String name = "unknown";

    public Singer( String name) {
        this.name = name;
    }

    public Singer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Singer singer = (Singer) o;
        return Objects.equals(name, singer.name);
    }



    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanScopeConfig.class);
        Singer singer1 = context.getBean(Singer.class);
        Singer singer2 = context.getBean(Singer.class);

        System.out.println("Identity Equal?: "+ (singer1==singer2));
        System.out.println("Value Equals?: "+ (singer1.equals(singer2)));
        System.out.println(singer1);
        System.out.println(singer2);
        System.out.println(singer1.name);
        System.out.println(singer2.name);
    }

    @Configuration
    public static class BeanScopeConfig{

        @Bean
        Singer singer(){
            return new Singer("Hello");
        }
    }
}
