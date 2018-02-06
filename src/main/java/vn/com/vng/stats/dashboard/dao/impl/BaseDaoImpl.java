package vn.com.vng.stats.dashboard.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Tanaye on 5/27/17.
 */
public abstract class BaseDaoImpl<T> {

    private static final Logger logger = Logger.getLogger(BaseDaoImpl.class.getName());

    protected List<T> queryForList(JdbcTemplate jdbc, String sqlQuery, Class<T> clazz) {
        List result = jdbc.query(sqlQuery,
                new BeanPropertyRowMapper(clazz));
        return result;
    }

    protected List<T> queryForList(DataSource ds, String sqlQuery, Class<T> clazz) throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(ds);
        List result = jdbc.query(sqlQuery,
                new BeanPropertyRowMapper(clazz));

        return result;
    }

    protected JdbcTemplate getConnection(JdbcTemplate jdbc) throws Exception {
        if (jdbc.getDataSource().getConnection().isValid(1)) {
            return jdbc;
        } else {
            DataSource dataSource = jdbc.getDataSource();
            return  new JdbcTemplate(dataSource);
        }
    }

    protected Connection getConnection(DataSource ds) throws Exception {
        Connection conn =  ds.getConnection();
        return conn;
    }
}
