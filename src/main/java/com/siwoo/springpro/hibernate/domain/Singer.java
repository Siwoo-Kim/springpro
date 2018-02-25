package com.siwoo.springpro.hibernate.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.GenerationType.IDENTITY;

@Entity @Table(name="singer")
@Getter @Setter @ToString(exclude = {"albums"})
@NamedQueries({
        @NamedQuery(name = "Singer.findAllWithAlbum",
        query = "select distinct s from Singer s " +
                "left join fetch s.albums a " +
                "left join fetch s.instruments i"),
        @NamedQuery(name="Singer.findById",
        query = "select distinct s from Singer s " +
                "left join fetch s.albums a " +
                "left join fetch s.instruments i " +
                "where s.id = :id"
        )
})
public class Singer implements Serializable{

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="birth_date")
    private LocalDateTime birthDate;
    @Version
    private int version;

    @OneToMany(mappedBy = "singer",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Album> albums = new HashSet<>();

    public boolean addAlum(Album album){
        album.setSinger(this);
        return getAlbums().add(album);
    }

    public void removeAlbum(Album album){
        getAlbums().remove(album);
    }

    /*many to many relation requires a join table*/
    @ManyToMany
    @JoinTable(name="singer_instrument",
    joinColumns = @JoinColumn(name="singer_id"),
    inverseJoinColumns = @JoinColumn(name="instrument_id"))
    private Set<Instrument> instruments = new HashSet<>();
}
