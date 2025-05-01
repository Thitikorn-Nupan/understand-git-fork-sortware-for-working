package com.ttknp.testspringbootapp.helper;

import com.ttknp.testspringbootapp.entities.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JdbcHelper {

    private final JdbcTemplate jdbcTemplate;

    public JdbcHelper(DataSource dataSourceSQL) {
        this.jdbcTemplate = new JdbcTemplate(dataSourceSQL);
    }

    // (1)
    // Note!
    // this.jdbcTemplate.update(...) work for update,delete,insert statements
    public int executeSQLStatement(String statement, Object... params) { // Object... theValues can be (sql,1,"1",'1',true) or null (sql)
        return this.jdbcTemplate.update(statement, params);
    }

    // (2)
    // Query by another jdbc template instance same (1)
    public int executeSQLStatement(DataSource dataSource, String statement, Object... params) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int rowAffected = jdbcTemplate.update(statement, params);
        jdbcTemplate = null;
        return rowAffected;
    }


    // (3)
    // same (1),(2) , but difference type params
    public <E> int executeSQLStatement(DataSource dataSource, String statement, List<E> params) {
        log.info("params as list {}",params.toString()); // [Jacky, Aldo, 29, Jacky@aldo.com, 0913231933, 3]
        Object[] paramArray = params.toArray();
        log.info("params as object {}",paramArray); // Jacky, Aldo, 29, Jacky@aldo.com, 0913231933, 3
        return this.executeSQLStatement(dataSource, statement, paramArray);
    }


    // (4)
    /**
     example !
     public Integer getCountOfGenderInAGrade(String gender, Integer grade) {
     String sql = "select count(1) as total from student where gender = ? and grade = ?";
     // **
     return jdbcTemplate.queryForObject(sql, Integer.class, gender, grade);
     }
    */
    public <T> T executeSQLStatementForObject(Class<T> aClass, String statement, Object... params) {
        // queryForObject(...) is meant for handling a DB query result with a single row !
        // queryForObject(...) With Class<T> it returns follow generic type
        // Integer userId = queryForObject("sql statement", Integer.clas, 1,"1",'1',true)
        return this.jdbcTemplate.queryForObject(statement, aClass, params);
    }

    // (4.1)
    // same (4) but difference type params
    public <T, E> T executeSQLStatementForObject(Class<T> theClass, String theSQL, List<E> params) {
        log.info("params as list {}",params.toString());
        Object[] paramArray = params.toArray();
        log.info("params as object {}",paramArray);
        return this.executeSQLStatementForObject(theClass, theSQL, paramArray);
    }

    // (5) ********************************************* queryForObject(...) for single row
    // Both (5),(6) use the same RowMapper class !
    // So you inherit only 1 RowMapper (method) and you'll work with methods that return T or List<T> !
    public <T> T executeSQLStatementForObject(String statement, RowMapper<T> rowMapper, Object... params) {
        // User user = queryForObject("select * from A_APP.USERS_DETAIL where ID = ?;", (rs,rowNum) -> {...} , 1)
        return this.jdbcTemplate.queryForObject(statement, rowMapper , params); // should get only 1 ** follow generic type
    }

    // (6) ********************************************* query(...) for list
    public <T> List<T> executeSQLStatementForObject(String statement, RowMapper<T> rowMapper) {
        // List<User> users = queryForObject("select * from A_APP.USERS_DETAIL;", (rs,rowNum) -> {...})
        return this.jdbcTemplate.query(statement, rowMapper); // should get as array list ** follow generic type
    }


    // (7)
    // Both (7),(8) use the same RowMapper class !
    // return as object as Map {keys : values } as array
    public <E> Map<String, String> executeSQLStatementForObject(String statement, List<E> params) {
        // sql statement , map , object
        return this.jdbcTemplate.queryForObject(statement, (rs, rowNum) -> mapRow(rs) , params.toArray());
    }

    // (8)
    // return as object as Map {keys : values } [] as list as array
    public List< Map<String, String> > executeSQLStatementForObject(String statement, Object ...params) {
        return this.jdbcTemplate.query(statement, (rs, rowNum) -> mapRow(rs) , params );
    }

    //
    private Map<String, String> mapRow(ResultSet rs) throws SQLException {
        Map<String, String> rowMap = new LinkedHashMap<>();
        /**
          ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM TABLE2");
          ResultSetMetaData rsmd = rs.getMetaData();
          int numberOfColumns = rsmd.getColumnCount();
          boolean b = rsmd.isSearchable(1);
       */
        ResultSetMetaData metaData = rs.getMetaData();
        // log.info("metaData {}", metaData.toString()); // SQLServerResultSetMetaData:1
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnLabel(i);
            String columnValue = rs.getString(i);
            rowMap.put(columnName, columnValue);
        }
        // ex result {ID=3, FIRSTNAME=Jacky, LASTNAME=Aldo, AGE=29, EMAIL=Jacky@aldo.com, PHONE=0913231933, DATE_CREATED=000000000001DC96}
        // it's very easy to loop and convert to java pojo
        return rowMap;
    }

}
