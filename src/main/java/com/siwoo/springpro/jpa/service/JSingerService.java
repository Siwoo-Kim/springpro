package com.siwoo.springpro.jpa.service;

import com.siwoo.springpro.jpa.domain.JSinger;

import java.util.List;

public interface JSingerService {
    List<JSinger> findAll();
    List<JSinger> findAllWithAlbum();
    JSinger findById(Long id);
    JSinger save(JSinger singer);
    void delete(JSinger singer);
    List<JSinger> findAllByNativeQuery();

}
