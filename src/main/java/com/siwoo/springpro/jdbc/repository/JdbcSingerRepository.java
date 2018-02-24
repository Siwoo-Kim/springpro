package com.siwoo.springpro.jdbc.repository;

import com.siwoo.springpro.jdbc.domain.Album;
import com.siwoo.springpro.jdbc.domain.Singer;
import com.siwoo.springpro.jdbc.exception.MySqlErrorCodesTranslator;
import lombok.Setter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcSingerRepository implements SingerRepository, InitializingBean {
    /*
        @Autowired private JdbcTemplate jdbcTemplate;
        private static final String FINDNAME_BY_ID_SQL = "select first_name || ' ' || last_name from singer where id = ?";
    */
    @Autowired NamedParameterJdbcTemplate jdbcTemplate;


    /*init method*/
    @Override
    public void afterPropertiesSet() throws Exception {
        if(jdbcTemplate == null)
            throw new BeanCreationException("Null JdbcTemplate on JdbcSingerRepository");
    }

    private static RowMapper<Singer> singerMapper = (resultSet,rowNum)->{
        Singer singer = new Singer();
        singer.setId(resultSet.getLong("id"));;
        singer.setFirstName(resultSet.getString("first_name"));
        singer.setLastName(resultSet.getString("last_name"));
        singer.setBirthDate(resultSet.getDate("birth_date"));
        return singer;
    };

    private static final String FINDALL_SQL="select id, first_name, last_name, birth_date from singer";
    @Override
    public List<Singer> findAll() {
        return jdbcTemplate.query(FINDALL_SQL,singerMapper);
    }

    private static final String FINDNAME_BY_ID_SQL = "select first_name || ' ' || last_name from singer where id = :singerId";
    @Override
    public String findNameById(Long id) {
        /*return jdbcTemplate
                .queryForObject(
                        FINDNAME_BY_ID_SQL,
                        new Object[]{id},
                        String.class);*/
        Map<String,Object> params = new HashMap<>();
        params.put("singerId",id);
        return jdbcTemplate.queryForObject(FINDNAME_BY_ID_SQL,params,String.class);
    }

    private static final String FINDALL_WITH_ALBUMS_SQL = "select s.id, s.first_name, s.last_name, s.birth_date" +
            ", a.id as album_id, a.title, a.release_date from singer s " +
            "left join album a on s.id = a.singer_id";
    private static ResultSetExtractor<List<Singer>> singerWithAlbumExtractor = (resultSet) ->{
          Map<Long,Singer> map = new HashMap<>();
          Singer singer;
          while (resultSet.next()){
              Long id = resultSet.getLong("id");
              singer = map.get(id);
              /*if singer does not exists in map*/
              if(singer == null){
                  singer = new Singer();
                  singer.setId(id);
                  singer.setFirstName(resultSet.getString("first_name"));
                  singer.setLastName(resultSet.getString("last_name"));
                  singer.setBirthDate(resultSet.getDate("birth_date"));
                  singer.setAlbums(new ArrayList<>());
                  map.put(id,singer);
              }
              Long albumId = resultSet.getLong("album_id");
              /*if album is exists*/
              if(albumId > 0){
                  Album album = new Album();
                  album.setId(albumId);
                  album.setSingerId(id);
                  album.setTitle(resultSet.getString("title"));
                  album.setReleaseDate(resultSet.getDate("release_date"));
                  singer.addAlbum(album);
              }
          }
          return new ArrayList<>(map.values());
    };
    @Override
    public List<Singer> findAllWithAlbums(){
        return jdbcTemplate.query(FINDALL_WITH_ALBUMS_SQL,singerWithAlbumExtractor);
    }


    @Override
    public List<Singer> findByFirstName(String firstName) {
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
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void update(Singer singer) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void delete(Long singerId) {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public List<Singer> findAllWithDetail() {
        throw new UnsupportedOperationException("Unsupported method");
    }

    @Override
    public void insertWithDetail(Singer singer) {
        throw new UnsupportedOperationException("Unsupported method");
    }

}
