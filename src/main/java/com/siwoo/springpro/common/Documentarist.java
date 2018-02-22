package com.siwoo.springpro.common;

public class Documentarist {

    GrammyGuitarist grammyGuitarist;

    public void execute(){
        grammyGuitarist.sing();
        grammyGuitarist.talk();
    }
    public  void setDep(GrammyGuitarist grammyGuitarist){
        this.grammyGuitarist = grammyGuitarist;
    }
}
