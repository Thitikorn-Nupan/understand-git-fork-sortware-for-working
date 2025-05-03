package com.ttknp.testspringbootapp.configuration;


import com.ttknp.testspringbootapp.services.StudentExtraServiceForCustomDriverConfigAndServiceByXML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;


// Config bean on xml
@Slf4j
@Configuration
@ImportResource("classpath:xml/spring-context-mysql-extra-a-database.xml")
public class CustomDriverConfigPassConstructorByXML {

    @Bean(name = "extraServiceA")
    public StudentExtraServiceForCustomDriverConfigAndServiceByXML setStudentExtraAServicePassConstructor(DataSource dataSourceExtraA ) {
        return new StudentExtraServiceForCustomDriverConfigAndServiceByXML(dataSourceExtraA);
    }



}
