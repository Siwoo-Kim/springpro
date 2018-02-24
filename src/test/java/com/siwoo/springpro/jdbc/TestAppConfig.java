package com.siwoo.springpro.jdbc;

import com.siwoo.springpro.jdbc.config.AppConfig;
import com.siwoo.springpro.jdbc.domain.Singer;
import com.siwoo.springpro.jdbc.repository.JdbcSingerRepository;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterThrowing;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.util.List;

import static com.siwoo.springpro.jdbc.TestJdbcSingerRepository.checkSinger;
import static org.junit.Assert.*;

@Log
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestAppConfig {

    @Autowired ApplicationContext context;
    @Autowired JdbcSingerRepository jdbcSingerRepository;

    @Test
    public void testConnection(){
        assertNotNull(context);
        assertNotNull(context.getBean(DataSource.class));
    }

    @Test
    public void testFindAll(){
        jdbcSingerRepository
                .findAll()
                .stream()
                .forEach(checkSinger);

    }

    @Test
    public void testFindByFirstName(){
        List<Singer> singerList = jdbcSingerRepository.findByFirstName("John");
        log.warning(singerList::toString);
        assertTrue(singerList.size() == 2);
    }



}
