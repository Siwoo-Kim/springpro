package com.siwoo.springpro.aop.introduction;

import com.siwoo.springpro.common.Contact;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.framework.ProxyFactory;

public class IntroductionDemo {

    public static void main(String[] args) {
        Contact target = new Contact();
        target.setName("John Mayer");

        IntroductionAdvisor advisor = new IsModifiedAdvisor();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(target);
        pf.setOptimize(true);

        Contact proxy = (Contact) pf.getProxy();
        IsModified proxyInterface = (IsModified) proxy;

        System.out.println("Is Contact?: "+(proxy instanceof Contact));
        System.out.println("Is IsModifed? "+(proxy instanceof IsModified));
        System.out.println("Has bean modified?: "+proxyInterface.isModified());

        proxy.setName("John Mayer");
        System.out.println("Has bean modified?: "+proxyInterface.isModified());

        proxy.setName("Eric Clapton");
        System.out.println("Has bean modified?: "+proxyInterface.isModified());

    }
}
