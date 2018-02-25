package com.siwoo.springpro.jpa;

import com.siwoo.springpro.jpa.config.JpaConfig;
import com.siwoo.springpro.jpa.domain.JSinger;
import com.siwoo.springpro.jpa.service.JSingerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.function.Consumer;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class TestJSingerServiceImpl {

    @Autowired JSingerService singerService;
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
}



























