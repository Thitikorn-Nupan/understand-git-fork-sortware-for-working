package com.ttknp.testspringbootapp.configuration;


import com.ttknp.testspringbootapp.services.StudentExtraServiceForCustomDriverConfigAndServiceByXML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;



@Slf4j
@Configuration
@ImportResource("classpath:xml/spring-context-mysql-extra-a-database.xml")
public class CustomDriverConfigAndServicePassConstructorByXML {

    @Bean(name = "extraServiceA")
    public StudentExtraServiceForCustomDriverConfigAndServiceByXML setStudentExtraAServicePassConstructor(@Qualifier("dataSourceMySQLExtraA") DataSource dataSourceExtraC ) {
        return new StudentExtraServiceForCustomDriverConfigAndServiceByXML(dataSourceExtraC);
    }



}
