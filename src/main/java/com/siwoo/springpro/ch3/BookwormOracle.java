package com.siwoo.springpro.ch3;

import org.springframework.stereotype.Component;

@Component("oracle")
public class BookwormOracle implements Oracle {
    private Encyclopeida encyclopeida;

    public void setEncyclopeida(){
        this.encyclopeida = encyclopeida;
    }

    public String defineMeaningOfLife() {
        return "Encyclopedias are a waste of money - go see the world instead";
    }
}
