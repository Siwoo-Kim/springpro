package com.siwoo.springpro.hibernate;

import com.siwoo.springpro.hibernate.config.HibernateConfig;
import com.siwoo.springpro.hibernate.domain.Album;
import com.siwoo.springpro.hibernate.domain.Singer;
import com.siwoo.springpro.hibernate.repository.SingerRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class TestHibernateSingerRepository {

    @Autowired
    SingerRepository singerRepository;

    private Consumer<Singer> listSingers = singer -> {
        log.warn(singer.toString());
        log.warn("Intrument of "+singer.getLastName()+": ");
        singer.getAlbums().stream().map(Album::toString).forEach(log::warn);
    };


    @Before
    public void setup(){
        assertNotNull(singerRepository);
    }

    @Test @Transactional
    public void testInsert(){
        Long previouseCount = singerRepository.count();
        Singer singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDateTime.of(1940,8,16,0,0));

        Album album = new Album();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(LocalDateTime.of(1961,7,18,0,0));
        singer.addAlum(album);

        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDateTime.of(1962,3,20,0,0));
        singer.addAlum(album);

        singerRepository.save(singer);
        assertNotNull(singer.getId());

        List<Singer> singers = singerRepository.findAllWithAlbums();
        assertEquals(previouseCount+1,singers.size(),0);

        singers.forEach(listSingers);
        singers.stream().filter(foundSinger -> "BB".equals(foundSinger.getFirstName()))
                .forEach(foundSinger -> {
                    assertEquals(foundSinger.getAlbums().size(),2,0);
                    foundSinger.getAlbums().stream().map(Object::toString).forEach(log::warn);
                });

    }

    @Test @Transactional
    public void testUpdate(){
        Singer singer = singerRepository.findById(1l);
        assertNotNull(singer);
        assertEquals("Mayer",singer.getLastName());

        Album album = singer.getAlbums()
                .stream()
                .filter(a -> "Battle Studies".equals(a.getTitle()))
                .findFirst().get();

        singer.setFirstName("John Clayton");
        singer.removeAlbum(album);
        //singerRepository.update(singer);
        singerRepository.save(singer);

        singerRepository.findAllWithAlbums().forEach(listSingers);

    }

    @Test
    public void testFindAll(){
        Singer singer = singerRepository.findById(2l);
        assertNotNull(singer);
        log.warn(singer.toString());
        singerRepository.delete(singer.getId());
        log.info(" ---- Listing singers:");

        try {
            singerRepository.findAll().forEach(listSingers);
        }catch (LazyInitializationException e){
            log.info("expect LazyInitializationException ---");
        }

        singerRepository.findAllWithAlbums().forEach(listSingers);
    }



}
