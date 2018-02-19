package com.siwoo.springpro.ch3.collection_bean;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CollectionInjection {
    @Resource(name="map")
    private Map<String,Object> map;
    @Resource(name="props")
    private Properties props;
    @Resource(name="set")
    private Set set;
    @Resource(name="list")
    private List list;

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(new ClassPathResource("META-INF/spring/ch3/app-context-annotation.xml"));
        context.refresh();

        CollectionInjection instance = context.getBean("injectCollection",CollectionInjection.class);
        instance.displayInfo();

        context.close();
    }

    private void displayInfo() {
        System.out.println("Map Contents:\n");
        map.entrySet().stream().forEach(entry -> {
            System.out.println("Key: "+ entry.getKey() + " - Value: "+entry.getValue());
        });

        System.out.println("\nProperties Contents:\n");
        props.entrySet().stream().forEach(entry -> {
            System.out.println("Key: "+ entry.getKey() + " - Value: "+entry.getValue());
        });

        System.out.println("\nSet Contents:\n");
        set.forEach(System.out::println);

        System.out.println("\nList Contents:\n");
        list.forEach(System.out::println);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public static class LyricHolder{
        private String lyric = "Ralr ralr ralr";

        public String getLyric() {
            return lyric;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }

        @Override
        public String toString() {
            return "LyricHolder{" +
                    "lyric='" + lyric + '\'' +
                    '}';
        }
    }
}
