package com.ttknp.testspringbootapp.services.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public abstract class ModelServiceCommon <T> implements RowMapper<T> {

    private String sqlScriptDirOnRoot = "sql/";

    public abstract List<T> getAllModels() ;

    public abstract <U,U2> List<T> getAllModelsSortBy(U modelKey,U2 modelKey2) ;

    // query script (it's file!) *** query by ResourceDatabasePopulator
    // you can have many sql command in script
    public void loadScript(String fileName, DataSource dataSource) {
        String fullSqlScriptDirOnRoot = sqlScriptDirOnRoot + fileName;
        log.info("Loading script from  {}" , fullSqlScriptDirOnRoot);
        // ** way to query with script sql ** if you want queries response don't do the way
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource(fullSqlScriptDirOnRoot)); // ClassPathResource class it looks to src of this module
        populator.execute(Objects.requireNonNull(dataSource)); // by default it'll log queries result on console
    }

    // query script (not file!) and pass params *** query by JdbcTemplate
    // (MYSQL ONLY) you can have only one sql command in script ex, reload-mysql-students-table-params.sql
    public void
    loadScript(Class aClass,String fileName, JdbcTemplate jdbcTemplateSQL, HashMap<String,String> params) throws SQLException, IOException {
        String fullSqlScriptDirOnRoot = sqlScriptDirOnRoot + fileName;
        StringBuilder stringBuilder = readSQLFileAsString(aClass,fullSqlScriptDirOnRoot);
        String sql = stringBuilder.toString();
        log.info("Loaded sql script  {}" , sql);

        int paramCount = params.size();
        String[] keys = new String[paramCount];
        String[] values = new String[paramCount];
        int i = 0;
        for (String key : params.keySet()) {
            keys[i] = key;
            i++;
        }
        i=0;
        for (String value : params.values()) {
            values[i] = value;
            i++;
        }
        for (i = 0; i < paramCount ; i++) {
            sql = sql.replace(keys[i], values[i]); // replace and update
            log.info("sql {}",sql);
        }

        // if do this way script must not contain the comment in sql script
        jdbcTemplateSQL.execute(sql); // you can execute many statements by execute(<queries>)
        log.info("Loaded sql script  {}" , sql);
    }

    // apply with insert select
    private StringBuilder readSQLFileAsString(Class aClass, String filePath) throws IOException {
        InputStream inputStream = aClass.getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        log.info("inputStream {} ", inputStream);
        // convert to useful form
        Scanner in = new Scanner(inputStream);
        // read contents
        StringBuilder sqlScriptBuilder = new StringBuilder();
        while (in.hasNext()) {
            sqlScriptBuilder.append(in.nextLine());
        }
        log.info("sqlScriptBuilder {}",sqlScriptBuilder.toString());
        // close file
        in.close();
        return sqlScriptBuilder;
    }


    // generic with method void ** note U can be any types
    // *** <U> is meaning type ex, <U> void test(U key),<U,U2,U3> void test(U key,U2 key2,U3 key3)
    // ex, removeModelByPk("1") removeModelByPk(1) , ...(true) , ...
    public abstract <U> void removeModelByPk(U modelPk);
    // *** but if you specify parameter like U ...key it can be any types
    // ex , removeModelByManyPkManyType(1,"0",true,new String(),...) ** you have to use looping to get each modelPk
    public abstract <U> void removeModelByManyPkManyType(U ...modelPk);
    public abstract <U,U2,U3> void removeModelBy3Pk(U modelPk,U2 modelPk2,U3 modelPk3);
    // generic with method return T type
    public abstract <U> T removeModelByPkAndAuth(U modelPk);
}
