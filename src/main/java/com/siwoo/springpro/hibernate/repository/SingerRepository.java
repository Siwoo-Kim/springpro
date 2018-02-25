package com.siwoo.springpro.hibernate.repository;

import com.siwoo.springpro.hibernate.domain.Singer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SingerRepository {

    @Transactional(readOnly = true)
    List<Singer> findAll();

    Singer findById(Long id);

    List<Singer> findAllWithAlbums();

    List<Singer> findByFirstName(String firstName);

    String findNameById(Long id);

    String findLastNameById(Long id);

    String findFirstNameById(Long id);

    Singer save(Singer singer);

    Singer update(Singer singer);

    void delete(Long singerId);

    void delete(Singer singer);

    List<Singer> findAllWithDetail();

    void insertWithAlbum(Singer singer);

    Long count();
}
