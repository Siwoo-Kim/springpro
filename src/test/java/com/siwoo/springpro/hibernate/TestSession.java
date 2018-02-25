package com.siwoo.springpro.hibernate;

import com.siwoo.springpro.hibernate.config.HibernateConfig;
import com.siwoo.springpro.jdbc.repository.SingerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class TestSession {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    SingerRepository singerRepository;
    @Test @Transactional
    public void testSession(){
        assertNotNull(applicationContext.getBean(SessionFactory.class));
        assertNotNull(singerRepository);
    }
}
