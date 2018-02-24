package com.siwoo.springpro.jdbc.repository;

import com.siwoo.springpro.jdbc.domain.Singer;

import java.util.List;

public interface SingerRepository {
    List<Singer> findAll();

    List<Singer> findAllWithAlbums();

    List<Singer> findByFirstName(String firstName);
    String findNameById(Long id);
    String findLastNameById(Long id);
    String findFirstNameById(Long id);
    void insert(Singer singer);
    void update(Singer singer);
    void delete(Long singerId);
    List<Singer> findAllWithDetail();
    void insertWithDetail(Singer singer);

}
