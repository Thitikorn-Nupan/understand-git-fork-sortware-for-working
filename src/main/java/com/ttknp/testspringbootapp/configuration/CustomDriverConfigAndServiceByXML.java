package com.ttknp.testspringbootapp.configuration;


import com.ttknp.testspringbootapp.services.StudentExtraServiceForCustomDriverConfigAndServiceByXML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;


// Config bean on xml
@Slf4j
@Configuration
@ImportResource({
        "classpath:xml/spring-context-mysql-extra-a-database.xml",
        "classpath:xml/spring-context-mysql-extra-b-database.xml",
        "classpath:xml/spring-context-mysql-extra-c-database.xml"
})
public class CustomDriverConfigAndServiceByXML {

    private DataSource dataSourceExtraA;
    private DataSource dataSourceExtraB;

    @Autowired
    // get & inject datasource by specify @Qualifier("dataSourceMySQLExtraA") bean name
    // before config to jdbc on service beans
    public CustomDriverConfigAndServiceByXML( @Qualifier("dataSourceMySQLExtraA") DataSource dataSourceExtraA ,
                                              @Qualifier("dataSourceMySQLExtraB") DataSource dataSourceExtraB) {
        this.dataSourceExtraA = dataSourceExtraA;
        this.dataSourceExtraB = dataSourceExtraB;
    }

    // These services i don't specify @Service because i use @Bean instead
    @Bean(name = "extraServiceA")
    public StudentExtraServiceForCustomDriverConfigAndServiceByXML setStudentExtraAService() {
        return new StudentExtraServiceForCustomDriverConfigAndServiceByXML(dataSourceExtraA);
    }

    @Bean(name = "extraServiceB")
    public StudentExtraServiceForCustomDriverConfigAndServiceByXML setStudentExtraBService() {
        return new StudentExtraServiceForCustomDriverConfigAndServiceByXML(dataSourceExtraB);
    }

    @Bean(name = "extraServiceC")
    // can do like this
    public StudentExtraServiceForCustomDriverConfigAndServiceByXML setStudentExtraAServicePassConstructor(@Qualifier("dataSourceMySQLExtraC") DataSource dataSourceExtraC ) {
        return new StudentExtraServiceForCustomDriverConfigAndServiceByXML(dataSourceExtraC);
    }

}
