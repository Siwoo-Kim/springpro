package com.siwoo.springpro.ch3.lookup_method_injection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("singer")
@Scope("prototype")
public class Singer {
    private String lyric = "I played a quick game of chess with the salt and pepper shaker";

    public void sing(){
        //System.out.println(lyric);
    }

}
