package com.siwoo.springpro.hibernate.repository;

import com.siwoo.springpro.hibernate.domain.Singer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Transactional @Repository("hibernateSingerRepository")
public class HibernateSingerRepository implements SingerRepository {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override @Transactional(readOnly = true)
    public List<Singer> findAll() {
        return getSession().createQuery("select s from Singer s").list();
    }

    @Override
    public Singer findById(Long id) {
        /*return getSession().find(Singer.class,id);*/
        return (Singer) getSession().getNamedQuery("Singer.findById")
                .setParameter("id",id)
                .uniqueResult();
    }
    @Override @Transactional(readOnly = true)
    public List<Singer> findAllWithAlbums() {
        return getSession().getNamedQuery("Singer.findAllWithAlbum").list();
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public String findNameById(Long id) {
        return null;
    }

    @Override
    public String findLastNameById(Long id) {
        return null;
    }

    @Override
    public String findFirstNameById(Long id) {
        return null;
    }

    @Override
    public Singer save(Singer singer) {
        getSession().saveOrUpdate(singer);
        log.info("Singer saved with id: "+singer.getId());
        return singer;
    }

    @Override
    public Singer update(Singer singer) {
        return save(singer);
    }

    @Override
    public void delete(Long singerId) {
        getSession().delete(findById(singerId));
    }

    @Override
    public void delete(Singer singer){
        getSession().delete(singer);
    }
    @Override
    public List<Singer> findAllWithDetail() {
        return null;
    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }

    @Override
    public Long count(){
        return (Long) getSession().createQuery("select count(*) from Singer s")
                .uniqueResult();
    }

}
