package vn.com.vng.stats.dashboard.config;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Created by Tanaye on 6/2/17.
 */
public abstract class BaseDB {

    protected static BasicDataSource getDataSource(String url, String userName, String pass) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword("pass");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        return ds;
    }
}
