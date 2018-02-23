package com.siwoo.springpro.jdbc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import javax.sql.DataSource;
import java.sql.Driver;

@Slf4j
@Configuration
@PropertySource(value="classpath:/META-INF/spring/jdbc/properties/jdbc.properties")
public class JdbcConfig1 {

    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    @Value("${jdbc.username}") //Conflict with environment name
    private String username;
    @Value("${password}")
    private String password;


    @Bean
    public DataSource dataSource(){
        log.warn(this.driverClassName);
        log.warn(this.url);
        log.warn(this.username);
        log.warn(this.password);
        try{
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(this.driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(this.url);
            dataSource.setUsername(this.username);
            dataSource.setPassword(this.password);
            return dataSource;
        }catch (Exception e){
            return null;
        }
    }
}
