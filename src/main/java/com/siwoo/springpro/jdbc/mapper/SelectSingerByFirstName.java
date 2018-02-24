package com.siwoo.springpro.jdbc.mapper;

import com.siwoo.springpro.jdbc.domain.Singer;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.function.Function;
import java.util.function.Supplier;

public class SelectSingerByFirstName extends MappingSqlQuery<Singer> {
    private static final String SQL_FIND_BY_FIRSTNAME =
            "select id, last_name, first_name, birth_date from singer " +
                    "where first_name = :first_name";

    public static Function<ResultSet,Singer> singerFucnction = (rs) -> {
        Singer singer = new Singer();
        try {
            singer.setId(rs.getLong("id"));
            singer.setFirstName(rs.getString("first_name"));
            singer.setLastName(rs.getString("last_name"));
            singer.setBirthDate(rs.getDate("birth_date"));
            return singer;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    };

    public SelectSingerByFirstName(DataSource ds) {
        super(ds,SQL_FIND_BY_FIRSTNAME);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
    }

    @Override
    protected Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return singerFucnction.apply(rs);
    }
}
