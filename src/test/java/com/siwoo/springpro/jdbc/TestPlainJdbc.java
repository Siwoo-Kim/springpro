package com.siwoo.springpro.jdbc;

import com.siwoo.springpro.jdbc.domain.Singer;
import com.siwoo.springpro.jdbc.repository.PlainSingerRepository;
import com.siwoo.springpro.jdbc.repository.SingerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Slf4j
public class TestPlainJdbc {

    private static SingerRepository singerRepository = new PlainSingerRepository();


    @Test
    public void testInert(){
        log.info("Listing initial singer data: ");

        listAllSingers();
        log.info("------------");
        log.info("Insert a new singer");

        Singer singer = new Singer();
        singer.setFirstName("Ed");
        singer.setLastName("Sheeran");
        singer.setBirthDate(new Date((new GregorianCalendar(1991,2,1991)).getTime().getTime()));
        singerRepository.insert(singer);
        listAllSingers();

        log.info("----------");
        log.info("Deleting the previous created singer");

        singerRepository.delete(singer.getId());

        log.warn("Listing singer data after new singer deleted:");

        listAllSingers();
    }

    private static void listAllSingers() {
        List<Singer> singerList = singerRepository.findAll();
        singerList.stream().map(Singer::toString).forEach(log::info);
    }
}
