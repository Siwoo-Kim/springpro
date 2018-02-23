package com.siwoo.springpro.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("documentarist")
public class NewDocumentarist extends Documentarist {

    @Autowired @Qualifier("johnMayer")
    public void setGrammyGuitarist(GrammyGuitarist grammyGuitarist) {
        this.grammyGuitarist = grammyGuitarist;
    }

    @Override
    public void execute() {
        grammyGuitarist.sing();
        grammyGuitarist.sing(new Guitar());
        Guitar guitar = new Guitar();
        guitar.setBrand("Gibson");
        grammyGuitarist.sing(guitar);

        grammyGuitarist.talk();
    }


}
