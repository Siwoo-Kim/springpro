package com.siwoo.springpro.jdbc.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Album {
    private Long id;
    private Long singerId;
    private String title;
    private Date releaseDate;
}
