package com.ttknp.testspringbootapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


// Config bean on xml
@Configuration
// We can import this XML configuration file into a configuration class using the @ImportResource annotation:
// It looks at on the resource folder
@ImportResource("classpath:xml/spring-context.xml") // can import multiple files ** try
public class CustomDriverConfigByXML {

// ** No need below , now you can access all beans on @ImportResource("classpath:xml/spring-context.xml")
// public NamedParameterJdbcTemplate jdbcTemplate; // same
//    public JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public CustomDriverConfigByXML(@Qualifier("dataSource2") DataSource dataSource) {
//        // jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//        jdbcTemplate = new JdbcTemplate(dataSource);
//    }


}
