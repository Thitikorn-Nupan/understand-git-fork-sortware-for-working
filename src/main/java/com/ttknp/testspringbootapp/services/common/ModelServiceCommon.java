package com.ttknp.testspringbootapp.services.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;


import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class ModelServiceCommon <T> implements RowMapper<T> {
    // add methods abs
    // add methods
    // then extends

    private String sqlScriptDirOnRoot = "sql/";


    public abstract List<T> getAllModels() ;


    public void loadScript(String fileName, DataSource dataSource) {
        String fullSqlScriptDirOnRoot = sqlScriptDirOnRoot + fileName;
        log.info("Loading script from  {}" , fullSqlScriptDirOnRoot);
        // ** way to query with script sql ** if you want queries response don't do the way
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource(fullSqlScriptDirOnRoot)); // ClassPathResource class it looks to src of this module
        populator.execute(Objects.requireNonNull(dataSource)); // by default it'll log queries result on console
    }

}
