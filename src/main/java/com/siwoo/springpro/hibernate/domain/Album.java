package com.siwoo.springpro.hibernate.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name="album")
@Getter @Setter @ToString
public class Album implements Serializable {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    @Column(name="release_date")
    private LocalDateTime releaseDate;
    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name="singer_id")
    private Singer singer;
    public void setSinger(Singer singer) {
        this.singer = singer;
    }
}
