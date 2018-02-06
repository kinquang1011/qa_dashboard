package vn.com.vng.stats.dashboard.config;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.logging.Logger;

/**
 * Created by Tanaye on 5/26/17.
 */

@Configuration
@EnableTransactionManagement
public class DatabaseWrite {

    private static final Logger logger = Logger.getLogger(DatabaseWrite.class.getName());

    @Autowired
    @Value("${spring.dsWrite.url}")
    private String mysqlLocation;

    @Autowired
    @Value("${spring.dsWrite.username}")
    private String mysqlUserName;

    @Autowired
    @Value("${spring.dsWrite.password}")
    private String mysqlPass;

    @Bean(name = "jdbcWrite")
    public JdbcTemplate getJdbcTemplate() {
        DriverManagerDataSource driver = new DriverManagerDataSource(mysqlLocation, mysqlUserName, mysqlPass);
        driver.setDriverClassName(MysqlDataSource.class.getName());
        return new JdbcTemplate(driver);
    }

    @Bean(name = "dsWrite")
    public DataSource dataSource() {
        return getJdbcTemplate().getDataSource();
    }

    @Bean(name = "txmWrite")
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getJdbcTemplate().getDataSource());
    }
}
