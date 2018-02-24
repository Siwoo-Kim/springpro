package com.siwoo.springpro.jdbc.repository;

import com.siwoo.springpro.jdbc.domain.Album;
import com.siwoo.springpro.jdbc.domain.Singer;
import com.siwoo.springpro.jdbc.mapper.SelectAllSingers;
import com.siwoo.springpro.jdbc.mapper.SelectSingerByFirstName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository("jdbcSingerRepository")
public class JdbcSingerRepository implements SingerRepository /*, InitializingBean */{
    /*
        @Autowired private JdbcTemplate jdbcTemplate;
        private static final String SQL_FINDNAME_BY_ID = "select first_name || ' ' || last_name from singer where id = ?";
    */
    @Autowired(required = false) NamedParameterJdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.selectAllSingers = new SelectAllSingers(dataSource);
        this.selectSingerByFirstName = new SelectSingerByFirstName(dataSource);
    }

    /*init method*/
    /*
    @Override
    public void afterPropertiesSet() throws Exception {
        if(jdbcTemplate == null)
            throw new BeanCreationException("Null JdbcTemplate on JdbcSingerRepository");
    }
    */
    private static RowMapper<Singer> singerMapper = (resultSet,rowNum)->{
        Singer singer = new Singer();
        singer.setId(resultSet.getLong("id"));;
        singer.setFirstName(resultSet.getString("first_name"));
        singer.setLastName(resultSet.getString("last_name"));
        singer.setBirthDate(resultSet.getDate("birth_date"));
        return singer;
    };


    private SelectAllSingers selectAllSingers;
    /*private static final String SQL_FINDALL ="select id, first_name, last_name, birth_date from singer";*/
    @Override
    public List<Singer> findAll() {
        /*return jdbcTemplate.query(SQL_FINDALL,singerMapper);*/
        return selectAllSingers.execute();
    }

    private static final String SQL_FINDNAME_BY_ID = "select first_name || ' ' || last_name from singer where id = :singerId";
    @Override
    public String findNameById(Long id) {
        /*return jdbcTemplate
                .queryForObject(
                        SQL_FINDNAME_BY_ID,
                        new Object[]{id},
                        String.class);*/
        Map<String,Object> params = new HashMap<>();
        params.put("singerId",id);
        return jdbcTemplate.queryForObject(SQL_FINDNAME_BY_ID,params,String.class);
    }

    private static final String SQL_FINDALL_WITH_ALBUMS = "select s.id, s.first_name, s.last_name, s.birth_date" +
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
        return jdbcTemplate.query(SQL_FINDALL_WITH_ALBUMS,singerWithAlbumExtractor);
    }


    private SelectSingerByFirstName selectSingerByFirstName;
    @Override
    public List<Singer> findByFirstName(String firstName) {
        Map<String,Object> params = new HashMap<>();
        params.put("first_name",firstName);
        return selectSingerByFirstName.executeByNamedParam(params);
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
    public void insertWithAlbum(Singer singer) {
        throw new UnsupportedOperationException("Unsupported method");
    }

}
