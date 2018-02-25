package com.siwoo.springpro.hibernate.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name="instrument")
@Getter @Setter @ToString(exclude = "singers")
public class Instrument implements Serializable {
    @Id @Column(name="instrument_id")
    private String instrumentId;

    @ManyToMany
    @JoinTable(name="singer_instrument",
    joinColumns = @JoinColumn(name="instrument_id"),
    inverseJoinColumns = @JoinColumn(name="singer_id"))
    Set<Singer> singers = new HashSet<>();
}
