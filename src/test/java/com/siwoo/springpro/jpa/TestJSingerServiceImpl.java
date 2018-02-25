package com.siwoo.springpro.jpa;

import com.siwoo.springpro.jpa.config.JpaConfig;
import com.siwoo.springpro.jpa.domain.JAlbum;
import com.siwoo.springpro.jpa.domain.JSinger;
import com.siwoo.springpro.jpa.service.JSingerService;
import com.siwoo.springpro.jpa.service.JSingerSummaryUntypeImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
@Transactional
public class TestJSingerServiceImpl {

    @Autowired JSingerService singerService;
    @Autowired JSingerSummaryUntypeImpl singerSummaryUntype;
    private Consumer<JSinger> listSingers = (singer) -> {
        log.warn(singer.toString());
    };
    private Consumer<JSinger> listSingersWithAlbums = (singer) -> {
        log.warn(singer.toString() + " \t " +
                "---- albums : "+singer.getAlbums().toString());

    };
    @Before
    public void setup(){
        assertNotNull(singerService);
    }

    @Test
    public void testFindAll(){
        List<JSinger> singers = singerService.findAll();
        assertEquals(singers.size(),3,0);
        singers.forEach(listSingers);
    }

    @Test
    public void testFindAllWithAlbum(){
        List<JSinger> singers = singerService.findAllWithAlbum();
        assertEquals(singers.size(),3,0);
        singers.forEach(listSingersWithAlbums);
    }

    @Test
    public void testFindById(){
        JSinger foundSinger = singerService.findById(2l);
        assertNotNull(foundSinger);
    }

    @Test
    public void testDisplayAllSingerSummary(){
        singerSummaryUntype.displayAllSingerSummary();
    }

    @Test
    public void testInsert(){
        JSinger singer = new JSinger();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDateTime.of(1940,8,16,0,0));

        JAlbum album = new JAlbum();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(LocalDate.of(1961,7,18));
        singer.addAlbum(album);

        album = new JAlbum();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDate.of(1962,3,20));
        singer.addAlbum(album);

        singerService.save(singer);
        assertNotNull(singer.getId());

        List<JSinger> singers = singerService.findAllWithAlbum();
        assertEquals(singers.size(),4);

    }
}



























