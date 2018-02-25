package com.siwoo.springpro.jpa.service;

import com.siwoo.springpro.jpa.domain.JSinger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Service("singerService") @Repository @Transactional(readOnly = true)
public class JSingerServiceImpl implements JSingerService {
    final static String SQL_FINDALL="select id, first_name, last_name, birth_date, version from singer";

    @PersistenceContext EntityManager entityManager;

    @Override
    public List<JSinger> findAll(){
        return entityManager
                .createNamedQuery(JSinger.NAMEDSQL_FINDALL,JSinger.class).getResultList();
    }

    @Override
    public List<JSinger> findAllWithAlbum() {
        return entityManager
                .createNamedQuery(JSinger.NAMEDSQL_FINDALL_WITH_ALBUM,JSinger.class)
                .getResultList();
    }

    @Override
    public JSinger findById(Long id) {
        TypedQuery<JSinger> query = entityManager.createNamedQuery(JSinger.NAMEDSQL_FIND_BY_ID,JSinger.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override @Transactional(readOnly = false)
    public JSinger save(JSinger singer) {
        if(singer.getId() == null){
            log.info("Inserting new singer");
            entityManager.persist(singer);
        }else{
            log.info("Updating new singer");
            entityManager.merge(singer);
        }
        return singer;
    }

    @Override @Transactional(readOnly = false)
    public void delete(JSinger singer) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public List<JSinger> findAllByNativeQuery() {
        throw new UnsupportedOperationException("");
    }

}
