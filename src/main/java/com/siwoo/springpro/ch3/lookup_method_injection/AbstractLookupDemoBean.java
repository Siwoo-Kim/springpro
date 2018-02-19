package com.siwoo.springpro.ch3.lookup_method_injection;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component("abstractLookupBean")
public class AbstractLookupDemoBean implements DemoBean{

    @Lookup("singer")
    public Singer getMySinger(){
        return null;
    }

    public void doSomething(){
        getMySinger().sing();
    }
}
