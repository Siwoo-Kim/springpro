package com.siwoo.springpro.ch3;

import com.siwoo.springpro.StartFirstApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class DeclaringSpringComponents {

    public static void main(String[] args) {
        /*GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.load(new ClassPathResource("META-INF/spring/ch3/xml-bean-factory-config.xml"));
        context.refresh();*/
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer messageRenderer = context.getBean("renderer",MessageRenderer.class);

        messageRenderer.render();

        InjectSimple injectSimple1 = context.getBean("injectSimple1",InjectSimple.class);
        InjectSimple injectSimple2 = context.getBean("injectSimple2",InjectSimple.class);
        InjectSimple injectSimple3 = context.getBean("injectSimple3",InjectSimple.class);

        System.out.println(injectSimple1 == injectSimple2);
        System.out.println("Default " +injectSimple1);
        System.out.println("Setter Injection "+injectSimple2);
        System.out.println("SPEL "+injectSimple3);
        context.close();
    }

    public static class InjectSimpleConfig{
        private String name = "John";
        private int age = 39;
        private boolean programmer = false;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public boolean isProgrammer() {
            return programmer;
        }
    }

    @Component
    public static class InjectSimple{
        @Value("#{injectSimpleConfig.name}")
        private String name = "Guest";
        @Value("#{injectSimpleConfig.age + 1}")
        private int age = 28;
        @Value("#{!injectSimpleConfig.programmer}")
        private boolean programmer = true;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isProgrammer() {
            return programmer;
        }

        public void setProgrammer(boolean programmer) {
            this.programmer = programmer;
        }

        @Override
        public String toString() {
            return "InjectSimple{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", programmer=" + programmer +
                    '}';
        }
    }

    @Configuration
    @ImportResource(locations = "classpath:/META-INF/spring/ch3/xml-bean-factory-config.xml")
    /*@ComponentScan("com.siwoo.springpro.ch3")*/
    public static class HelloWorldConfiguration{

        @Bean
        InjectSimple injectSimple1(){
            return  new InjectSimple();
        }

        @Bean
        InjectSimple injectSimple2(){
            InjectSimple injectSimple = new InjectSimple();
            injectSimple.setAge(99);
            injectSimple.setName("IamNotSimple");
            injectSimple.setProgrammer(false);
            System.out.println(injectSimple);
            return injectSimple;
        }

        @Bean
        String message(){
            return new String("I hope that someone gets my message in a bottle");
        }
        @Bean
        public MessageProvider configurableMessageProvider(){
            return new ConfigurableMessageProvider();
        }
/*
        @Bean
        public MessageProvider provider(){
            return new HelloWorldMessageProvider();
        }

        @Bean
        public MessageRenderer renderer(){
            StandardOutMessageRenderer renderer = new StandardOutMessageRenderer();
            renderer.setMessageProvider(provider());
            return renderer;
        }
*/

    }

    public interface MessageRenderer{
        void render();
        void setMessageProvider(MessageProvider messageProvider);
        MessageProvider getMessageProvider();
    }

    @Service("renderer")
    public static class StandardOutMessageRenderer implements MessageRenderer{
        @Autowired @Qualifier("configurableMessageProvider")
        private MessageProvider messageProvider;
        
        public void render() {
            if(messageProvider == null){
                throw new RuntimeException("You must set the property messageProvider of class: "+StandardOutMessageRenderer.class.getName());
            }
            System.out.println(messageProvider.getMessage());
        }
        public void setMessageProvider(MessageProvider messageProvider) {
            this.messageProvider = messageProvider;
        }
        public MessageProvider getMessageProvider() {
            return this.messageProvider;
        }
    }

    public interface MessageProvider {
        String getMessage();
    }

    @Component("provider")
    public static class HelloWorldMessageProvider implements MessageProvider{

        public String getMessage() {
            return "Hello World!";
        }
    }

    public static class ConfigurableMessageProvider implements MessageProvider{
        @Autowired
        private String message;

   /*     public ConfigurableMessageProvider(@Value("Configurable Message") String message) {
            this.message = message;
        }*/

        public String getMessage() {
            return this.message;
        }
    }
}
