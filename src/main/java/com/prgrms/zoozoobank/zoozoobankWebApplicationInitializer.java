package com.prgrms.zoozoobank;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import javax.sql.DataSource;


@Slf4j
public class zoozoobankWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        log.debug("Staring Server...");
    }

    @Configuration
    @ComponentScan(basePackages = "com.prgrms.zoozoobank")
    @EnableTransactionManagement
    static class RootConfig {

    }
}
