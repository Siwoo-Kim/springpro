package com.siwoo.springpro.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name="singer") @Getter @Setter @ToString(exclude = {"instruments","albums"})
@NamedQueries({
        @NamedQuery(name = JSinger.NAMEDSQL_FINDALL,
        query = "select s from JSinger s"),
        @NamedQuery(name=JSinger.NAMEDSQL_FIND_BY_ID,
        query = "select distinct s from JSinger s " +
                "left join fetch s.albums a" +
                "left join fetch s.instruments i " +
                "where s.id = :id"),
        @NamedQuery(name=JSinger.NAMEDSQL_FINDALL_WITH_ALBUM,
        query = "select distinct s from JSinger s " +
                "left join fetch s.albums a " +
                "left join fetch s.instruments i")})
@SqlResultSetMapping(
        name="singerResult",
        entities =@EntityResult(entityClass = JSinger.class) )
public class JSinger implements Serializable{

    public static final String NAMEDSQL_FINDALL = "Singer.findAll";
    public static final String NAMEDSQL_FIND_BY_ID ="Singer.findById";
    public static final String NAMEDSQL_FINDALL_WITH_ALBUM ="Singer.findAllWithAlbum";

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Long id;

    @Version
    private int version;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "singer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<JAlbum> albums = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "singer_instrument",
    joinColumns = @JoinColumn(name="singer_id"),
    inverseJoinColumns = @JoinColumn(name="instrument_id"))
    private Set<JInstruments> instruments = new HashSet<>();
}






















