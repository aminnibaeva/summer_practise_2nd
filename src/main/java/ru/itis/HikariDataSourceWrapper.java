package ru.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;

public class HikariDataSourceWrapper {
    private final Properties properties;

    public HikariDataSourceWrapper(Properties properties) {
        this.properties = properties;
    }

    public HikariDataSource getHikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPassword(properties.getProperty("db.password"));
        config.setUsername(properties.getProperty("db.user"));
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        return new HikariDataSource(config);
    }
}
