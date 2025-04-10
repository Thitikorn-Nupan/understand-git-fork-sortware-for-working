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

    // generic with method void ** note U can be any types
    // *** <U> is meaning type ex, <U> void test(U key),<U,U2,U3> void test(U key,U2 key2,U3 key3)
    // ex, removeModelByPk("1") removeModelByPk(1) , ...(true) , ...
    public abstract <U> void removeModelByPk(U modelPk);
    // *** but if you specify parameter like U ...key it can be any types
    // ex , removeModelByManyPkManyType(1,"0",true,new String(),...)
    public abstract <U> void removeModelByManyPkManyType(U ...modelPk);
    public abstract <U,U2,U3> void removeModelBy3Pk(U modelPk,U2 modelPk2,U3 modelPk3);

    // generic with method return T type
    public abstract <U> T removeModelByPkAndAuth(U modelPk);

}
