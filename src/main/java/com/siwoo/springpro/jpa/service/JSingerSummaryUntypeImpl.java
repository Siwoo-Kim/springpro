package com.siwoo.springpro.jpa.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("singerSummaryUntype") @Repository @Transactional(readOnly = true)
public class JSingerSummaryUntypeImpl {

    @PersistenceContext EntityManager entityManager;
    private final static String JPQL_FIND_SINGER_SUMMARY =
            "select s.firstName, s.lastName, a.title, a.releaseDate " +
                    "from JSinger s " +
                    "left join s.albums a " +
                    "where a.releaseDate = (select max(a2.releaseDate) " +
                    "from JAlbum a2 where a2.singer.id = s.id) ";

    public void displayAllSingerSummary(){
        List<Object[]> results  =  entityManager.createQuery(JPQL_FIND_SINGER_SUMMARY,Object[].class)
        .getResultList();

        for(Object[] row: results){
            System.out.println("firstName: "+row[0]+", lastName: "+row[1]+",title: "+row[2]+", releaseDate: "+row[3]);
        }
    }
}
