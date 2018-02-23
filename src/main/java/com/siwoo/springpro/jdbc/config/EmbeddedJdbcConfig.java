package com.siwoo.springpro.jdbc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;


@Slf4j
@Configuration
public class EmbeddedJdbcConfig {

    @Bean
    DataSource dataSource(){
        try{
            EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
            return databaseBuilder
                    .setType(EmbeddedDatabaseType.H2)
                    .addScripts(
                            "classpath:/META-INF/plain-jdbc/schema.sql",
                            "classpath:/META-INF/plain-jdbc/test-data.sql").build();
        }catch (Exception e){
            log.error("Embedded DataSource bean cannot be created!,",e);
            return null;
        }
    }
}
