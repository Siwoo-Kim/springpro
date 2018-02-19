package com.siwoo.springpro.ch3;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

public class DependencyInjection {

    public static void main(String[] args) {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader rdr = new XmlBeanDefinitionReader(factory);
        rdr.loadBeanDefinitions(new ClassPathResource("META-INF/spring/ch3/xml-bean-factory-config.xml"));
        Oracle oracle = (Oracle) factory.getBean("oracle");
        System.out.println(oracle.defineMeaningOfLife());
    }

    public interface Oracle{
        String defineMeaningOfLife();
    }

    @Component("oracle")
    public static class BookwormOracle implements Oracle {
        private Encyclopeida encyclopeida;

        public void setEncyclopeida(){
            this.encyclopeida = encyclopeida;
        }

        public String defineMeaningOfLife() {
            return "Encyclopedias are a waste of money - go see the world instead";
        }
    }

    private interface Encyclopeida {
    }



    public interface NewsletterSender{
        void setSmtpServer(String smtpServer);
        String getSmtpServer();
        void setFromAddress(String fromAddress);
        String getFromAddress();

        void send();
    }
}
