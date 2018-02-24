package com.siwoo.springpro.jdbc;

import com.siwoo.springpro.jdbc.config.EmbeddedJdbcConfig;
import com.siwoo.springpro.jdbc.domain.Album;
import com.siwoo.springpro.jdbc.domain.Singer;
import com.siwoo.springpro.jdbc.repository.SingerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.sql.DataSource;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedJdbcConfig.class)
public class TestJdbcSingerRepository {

    @Autowired SingerRepository singerRepository;
    @Autowired ApplicationContext context;

    @Test
    public void testH2DB(){
        assertNotNull(context.getBean(DataSource.class));
        testDao(singerRepository);
    }

    private void testDao(SingerRepository singerRepository) {
        String singerName = singerRepository.findNameById(1l);
        assertTrue("John Mayer".equals(singerName));
        log.info(singerName);
    }

    public static final Consumer<Singer> checkSinger = singer ->  {
        log.info(singer.toString());
        if(singer.getAlbums() != null){
            for(Album album : singer.getAlbums()){
                log.info("---"+album);
            }
        }
    };

    @Test
    public void testRowMapper(){
        List<Singer> singers = singerRepository.findAll();
        assertTrue(singers.size() == 3);

        singers.forEach(checkSinger);
    }

    @Test
    public void testFindAllWithAlbums(){
        List<Singer> singers = singerRepository.findAllWithAlbums();
        assertTrue(singers.size() == 3);

        singers.forEach(checkSinger
        );
    }

}
