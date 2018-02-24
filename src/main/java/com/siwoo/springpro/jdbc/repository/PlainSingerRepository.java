package com.siwoo.springpro.jdbc.repository;

import com.siwoo.springpro.jdbc.domain.Singer;
import lombok.extern.slf4j.Slf4j;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PlainSingerRepository implements SingerRepository {
    private static final String DB_URL ="jdbc:mysql://localhost:3306/prospring?useSSL=true";
    private static final String DB_USER = "java";
    private static final String DB_PASSWORD = "java";

    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            log.warn("Problem leading Db Driver!",e);
        }
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    }

    private void closeConnection(Connection connection){
        if(connection == null) { return; }
        try{
            connection.close();
        }catch (SQLException e){
            log.warn("Problem closing connection to the database!",e);
        }
    }
    @Override
    public List<Singer> findAll() {
        List<Singer> result = new ArrayList<>();
        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from singer");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Singer singer = new Singer();
                singer.setId(resultSet.getLong("id"));
                singer.setFirstName(resultSet.getString("first_name"));
                singer.setLastName(resultSet.getString("last_name"));
                singer.setBirthDate(resultSet.getDate("birth_date"));
                result.add(singer);
            }
            statement.close();
        }catch (SQLException e){
            log.warn("Problem when executing SELECT!",e);
        }finally {
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<Singer> findAllWithAlbums() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public String findNameById(Long id) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public String findLastNameById(Long id) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public String findFirstNameById(Long id) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void insert(Singer singer) {
        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into Singer (first_name, last_name, birth_date) " +
                    "values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,singer.getFirstName());
            statement.setString(2,singer.getLastName());
            statement.setDate(3,singer.getBirthDate());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                singer.setId(generatedKeys.getLong(1));
            }
            statement.close();
        }catch (SQLException e){
            log.warn("Problem executing INSERT",e);
        }finally {
            closeConnection(connection);
        }

    }

    @Override
    public void update(Singer singer) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void delete(Long singerId) {
        Connection connection = null;
        try{
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from singer where id = ?");
            statement.setLong(1,singerId);
            statement.execute();
            statement.close();
        }catch (SQLException e){
            log.info("Problem executing DELETE",e);
        }finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Singer> findAllWithDetail() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void insertWithAlbum(Singer singer) {
        throw new UnsupportedOperationException("Unsupported method");
    }
}
