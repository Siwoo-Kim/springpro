package com.siwoo.springpro.ch3;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class HierarchicalAppContextUsage {

    public static void main(String[] args) {
        GenericXmlApplicationContext parent = new GenericXmlApplicationContext();
        parent.load(new ClassPathResource("META-INF/spring/ch3/parent-context.xml"));
        parent.refresh();

        GenericXmlApplicationContext child = new GenericXmlApplicationContext();
        child.load(new ClassPathResource("META-INF/spring/ch3/child-context.xml"));
        child.setParent(parent);
        child.refresh();

        Song song1 = child.getBean("song1",Song.class);
        Song song2 = child.getBean("song2",Song.class);
        Song song3 = child.getBean("song3",Song.class);
        System.out.println(song1);
        System.out.println(song2);
        System.out.println(song3);

        child.close();
        parent.close();
    }
}
