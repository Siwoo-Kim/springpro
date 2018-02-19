package com.siwoo.springpro.ch3.lookup_method_injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("standardLookupBean")
public class StandardLookupDemoBean implements DemoBean{

    @Autowired @Qualifier("singer")
    private Singer mySinger;

    @Override
    public void doSomething() {
        mySinger.sing();
    }

    public Singer getMySinger() {
        return mySinger;
    }

    public void setMySinger(Singer mySinger) {
        this.mySinger = mySinger;
    }


}
