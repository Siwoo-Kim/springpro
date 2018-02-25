package com.siwoo.springpro.jpa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity @Table(name="instrument")
@Getter @Setter @ToString
public class JInstruments implements Serializable{

    @Id @Column(name="instrument_id")
    private String instrumentId;

    @ManyToMany
    @JoinTable(name="singer_instrument",
    joinColumns = @JoinColumn(name="instrument_id"),
    inverseJoinColumns = @JoinColumn(name="singer_id"))
    private Set<JSinger> singers = new HashSet<>();
}
