package com.siwoo.springpro;

import com.siwoo.springpro.config.HelloWorldConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class StartFirstApplication {

    public static void main(String[] args) {
        /*
            System.out.println("Hello World");
        */

        /*
        externalize the message content to build flexible application
            if(args.length > 0){
                System.out.println(args[0]);
            }else{
                System.out.println("Hello World!"); *//*but still main has obtain message logic, so it is not flexible*//*
            }
        */
        /*
            MessageRenderer mr = new StandardOutMessageRenderer();
            MessageProvider mp = new HelloMessageProvider();
            mr.setMessageProvider(mp);
            mr.render();
        */
        /*
            MessageRenderer messageRenderer = MessageSupportFactory.getInstance().getMessageRenderer();
            MessageProvider messageProvider = MessageSupportFactory.getInstance().getMessageProvider();

            messageRenderer.setMessageProvider(messageProvider);
            messageRenderer.render();
        */

        /*
            ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/app-context.xml");
        */

        ApplicationContext context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer renderer = context.getBean(MessageRenderer.class);
        renderer.render();

    }

    public interface MessageProvider{
        String getMessage();
    }

    public interface MessageRenderer{
        void render();
        void setMessageProvider(MessageProvider provider); /*java bean style*/
        MessageProvider getMessageProvider();
    }

    public static class HelloMessageProvider implements MessageProvider{
        public String getMessage() {
            return "Hello World!";
        }
    }

    public static class StandardOutMessageRenderer implements MessageRenderer{
        private MessageProvider messageProvider;

        public MessageProvider getMessageProvider() {
            return messageProvider;
        }

        public void setMessageProvider(MessageProvider messageProvider) {
            this.messageProvider = messageProvider;
        }

        public void render() {
            if(messageProvider==null){
                throw new RuntimeException("You must ste the property messageProvider of class: "+StandardOutMessageRenderer.class.getName());
            }
            System.out.println(messageProvider.getMessage());
        }
    }

    public static class MessageSupportFactory{
        private static MessageSupportFactory instance; /*signleton*/

        private Properties properties;
        private MessageProvider messageProvider;
        private MessageRenderer messageRenderer;

        static {
            instance = new MessageSupportFactory();
        }

        public static MessageSupportFactory getInstance(){
            return instance;
        }

        public MessageSupportFactory() {
            properties = new Properties();

            try{
                properties.load(this.getClass().getResourceAsStream("/msf.properties"));

                String rendererClass = properties.getProperty("renderer.class");
                String providerClass = properties.getProperty("provider.class");

                messageRenderer = (MessageRenderer) Class.forName(rendererClass).newInstance();
                messageProvider = (MessageProvider) Class.forName(providerClass).newInstance();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public MessageProvider getMessageProvider() {
            return messageProvider;
        }

        public MessageRenderer getMessageRenderer() {
            return messageRenderer;
        }
    }
}






















