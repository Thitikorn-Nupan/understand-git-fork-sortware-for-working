package com.ttknp.testspringbootapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    @Primary
    // ** fix No qualifying bean of type ...@Reposioties on jdbc
    // have to mark @Primary if you have multiple bean that's same
    // if i you do not do your @Repositories won't work
    public DriverManagerDataSource dataSource () {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource ;
    }
}
