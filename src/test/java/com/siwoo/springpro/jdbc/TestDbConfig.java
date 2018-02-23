package com.siwoo.springpro.jdbc;

import com.siwoo.springpro.jdbc.config.EmbeddedJdbcConfig;
import com.siwoo.springpro.jdbc.config.JdbcConfig1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;


@Slf4j
public class TestDbConfig {

    @Test
    public void testOne() throws SQLException {
        GenericXmlApplicationContext context =
                new GenericXmlApplicationContext("classpath:/META-INF/spring/jdbc/jdbc-context1.xml");
        DataSource dataSource = context.getBean(DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);

        context.close();
    }

    @Test
    public void testTwo() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig1.class);
        DataSource dataSource = context.getBean(DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);

        context.close();
    }
/*
    @Test
    public void testThree() throws SQLException {
        GenericXmlApplicationContext context =
                new GenericXmlApplicationContext("classpath:/META-INF/spring/jdbc/embedded-h2-cfg.xml");
        DataSource dataSource = context.getBean(DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);

        context.close();
    }
*/

    @Test
    public void testFour() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class);
        DataSource dataSource = context.getBean(DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);

        context.close();
    }

    private void testDataSource(DataSource dataSource) throws SQLException {
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select 1");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int mockVal = resultSet.getInt("1");
                assertTrue(mockVal==1);
            }
            statement.close();
        }catch (Exception e){
            log.debug("Something unexpected happend.",e);
        }finally {
            if(connection == null){
                connection.close();
            }
        }
    }

}
