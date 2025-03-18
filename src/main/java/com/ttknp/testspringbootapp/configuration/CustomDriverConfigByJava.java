package com.ttknp.testspringbootapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:info/configs.properties") // @PropertySource("classpath:") using to include properties file
public class CustomDriverConfigByJava {

    private DriverManagerDataSource dataSource;
    @Value("${DRIVER}")
    private String driver;
    @Value("${URL}")
    private String url;
    // Should not use key as USERNAME / PASSWORD
    // Because i was get value from another prop
    @Value("${SQL.USERNAME}")
    private String username;
    @Value("${SQL.PASSWORD}")
    private String password;


    @Bean("dataSource1")
    public DriverManagerDataSource dataSource () {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource ;
    }
}
