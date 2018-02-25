package com.siwoo.springpro.jpa.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @ToString
public class SingerSummary implements Serializable{
    private String firstName;
    private String lastName;
    private String lastestAlbum;

    public SingerSummary(String firstName, String lastName, String lastestAlbum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastestAlbum = lastestAlbum;
    }

}
