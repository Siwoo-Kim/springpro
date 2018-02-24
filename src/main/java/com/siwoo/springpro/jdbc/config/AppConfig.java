package com.siwoo.springpro.jdbc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@ComponentScan("com.siwoo.springpro.jdbc.repository")
@PropertySource("classpath:/META-INF/app-properties/database.properties")
public class AppConfig implements InitializingBean{

    @Value("${database.driverClassName}")
    private String driverClassName;
    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(driverClassName);
        log.info(url);
        log.info(username);
        log.info(password);
    }

    @Bean
    public DataSource dataSource(){
        try{
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        }catch (Exception e){
            log.error("DBCP DataSource bean cannot be created!",e);
            return null;
        }
    }
}
