package com.siwoo.springpro.jdbc.mapper;

import com.siwoo.springpro.jdbc.domain.Singer;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.siwoo.springpro.jdbc.mapper.SelectSingerByFirstName.singerFucnction;

public class SelectAllSingers extends MappingSqlQuery<Singer> {
    private static final String SQL_SELECT_ALL_SINGER =
            "select id, first_name, last_name, birth_date " +
                    "from singer";

    public SelectAllSingers(DataSource dataSource){
        super(dataSource, SQL_SELECT_ALL_SINGER);
    }
    @Override
    protected Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
       return singerFucnction.apply(rs);
    }
}
