package com.siwoo.springpro.jpa;

import com.siwoo.springpro.jpa.config.JpaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class TestJpaConfig {
    @Autowired
    ApplicationContext applicationContext;
    @PersistenceContext
    EntityManager entityManager;

    @Test @Transactional
    public void testEntityManager(){
        assertNotNull(applicationContext);
        assertNotNull(entityManager);
    }
}
