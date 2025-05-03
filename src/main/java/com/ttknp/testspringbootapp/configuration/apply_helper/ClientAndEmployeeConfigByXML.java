package com.ttknp.testspringbootapp.configuration.apply_helper;

import com.ttknp.testspringbootapp.services.StudentExtraServiceForCustomDriverConfigAndServiceByXML;
import com.ttknp.testspringbootapp.services.apply_helper.ClientService;
import com.ttknp.testspringbootapp.services.apply_helper.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

// Auto inject
@Configuration
@ImportResource({"classpath:xml/spring-context-mysql-extra-d-database.xml","classpath:xml/spring-context-mysql-extra-e-database.xml"}) // can import multiple files ** try
public class ClientAndEmployeeConfigByXML {

    @Bean(name = "clientService")
    public ClientService setClientService(@Qualifier("dataSourceMySQLExtraD") DataSource dataSourceExtraD ) {
        return new ClientService(dataSourceExtraD);
    }

    @Bean(name = "employeeService")
    public EmployeeService EmployeeService(@Qualifier("dataSourceMySQLExtraE") DataSource dataSourceExtraE ) {
        return new EmployeeService(dataSourceExtraE);
    }
}
