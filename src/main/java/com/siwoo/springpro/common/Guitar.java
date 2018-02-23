package com.siwoo.springpro.common;

import lombok.Getter;
import lombok.Setter;

public class Guitar {
    @Getter @Setter
    private String brand = " Martin";

    public String play(){
        return "G C G C Am D7";
    }


}
