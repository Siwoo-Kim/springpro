package com.siwoo.springpro.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity @Table(name="album") @NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter @Setter @ToString
public class JAlbum implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private String title;

    @Column(name="release_date")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name="singer_id")
    private JSinger singer;

    public JAlbum(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }
}
