package com.siwoo.springpro.common;

public class NewDocumentarist extends Documentarist {

    @Override
    public void execute() {
        grammyGuitarist.sing();
        grammyGuitarist.sing(new Guitar());
        grammyGuitarist.talk();
    }

}
