package com.siwoo.springpro.jpa.service;

import com.siwoo.springpro.jpa.view.SingerSummary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("singerSummaryService") @Repository @Transactional(readOnly = true)
public class JSingerSummaryServiceImpl implements JSingerSummaryService {
    @PersistenceContext EntityManager entityManager;

    @Override
    public List<SingerSummary> findAll() {
        return entityManager.createQuery(
                "select new com.siwoo.springpro.jpa.view.SingerSummary( s.firstName, s.lastName, a.title ) " +
                        "from JSinger s left join s.albums a " +
                        "where a.releaseDate = (select max(a2.releaseDate) from JAlbum a2 where a2.singer.id = s.id )", SingerSummary.class
        ).getResultList();
    }
}
