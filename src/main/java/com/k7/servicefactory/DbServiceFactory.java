package com.k7.servicefactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k7.configuration.AppProperties;
import com.k7.configuration.DbProperties;
import com.k7.requestfactory.HttpRequestFactory;
import com.k7.requestfactory.JsonHttpRequestFactory;
import com.k7.services.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.net.http.HttpClient;
import java.util.Properties;

@RequiredArgsConstructor
public class DbServiceFactory implements ServiceFactory {
    private final DbProperties dbProperties;
    private int userId = 0;


    @Override
    public ContactService createContactServices() {
        ContactService contactService = new DbContactService(getDbConfig(), getDataSource(), userId);
        return contactService;
    }

    @Override
    public UserService createUserServices() {
        UserService userService = new DbUserService(getDbConfig(), getDataSource(), userId, false);
        return userService;
    }

    private HikariConfig getDbConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbProperties.getJdbcUrl());
        config.setUsername(dbProperties.getDbUser());
        config.setPassword(dbProperties.getDbPass());
        config.setMaximumPoolSize(8);
        config.setMinimumIdle(4);
        return config;
    }

    private DataSource getDataSource() {
        return new HikariDataSource(getDbConfig());
    }
}
