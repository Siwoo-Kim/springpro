package com.siwoo.springpro.jpa;

import com.siwoo.springpro.jpa.config.JpaConfig;
import com.siwoo.springpro.jpa.domain.JAlbum;
import com.siwoo.springpro.jpa.service.JSingerSummaryService;
import com.siwoo.springpro.jpa.view.SingerSummary;
import lombok.extern.slf4j.Slf4j;
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
public class TestSingerSummaryService {

    @Autowired
    JSingerSummaryService singerSummaryService;

    private static Consumer<SingerSummary> listSingerSummary = singerSummary -> {
        log.warn(singerSummary.toString());
    };

    @Test
    public void testFindAll(){
        List<SingerSummary> singerSummarys = singerSummaryService.findAll();
        singerSummarys.forEach(listSingerSummary);
        assertEquals(singerSummarys.size(),2,0);

    }
}
