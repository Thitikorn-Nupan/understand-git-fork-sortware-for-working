package com.ttknp.testspringbootapp.services;

import com.ttknp.testspringbootapp.entities.UserDetail;
import com.ttknp.testspringbootapp.helper.JdbcHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserDetailServiceHelper implements RowMapper<UserDetail> {

    private List<UserDetail> userDetails;
    private JdbcHelper jdbcHelper;
    private DataSource dataSourceSQLExtra;

    @Autowired
    public UserDetailServiceHelper(@Qualifier("dataSourceSQL") DataSource dataSourceSQL,@Qualifier("dataSourceSQLExtra") DataSource dataSourceSQLExtra) {
        this.jdbcHelper = new JdbcHelper(dataSourceSQL);
        this.dataSourceSQLExtra = dataSourceSQLExtra;
        this.userDetails = new ArrayList<>();
    }

    public void updateFirstnameById(String firstname,Integer id) {
        String sql = "update A_APP.USERS_DETAIL set FIRSTNAME = ? where ID = ?;";
        int rowAffected = jdbcHelper.executeSQLStatement(sql, firstname,id);
        if (rowAffected > 0) {
            log.debug("Successfully updated with id {}", id);
        } else {
            log.debug("Failed to update with id {}", id);
        }
    }

    public int updateFirstnameByIdMoreThan(String firstname,Integer id) {
        String sql = "update A_APP.USERS_DETAIL set FIRSTNAME = ? where ID >= ?;";
        int rowAffected = jdbcHelper.executeSQLStatement(sql,firstname,id);
        if (rowAffected > 0) {
            return rowAffected;
        } else {
            return -1;
        }
    }

    public int updateById(UserDetail userDetail, Integer id) {
        String sql = "update A_APP.USERS_DETAIL set FIRSTNAME = ?, LASTNAME = ?, AGE = ?, EMAIL = ?, PHONE = ? where ID = ?;";
        List<String> params = new ArrayList<>();
        params.add(userDetail.firstname);
        params.add(userDetail.lastname);
        params.add(userDetail.age.toString());
        params.add(userDetail.email.toString());
        params.add(userDetail.phone.toString());
        params.add(id.toString());
        int rowAffected = jdbcHelper.executeSQLStatement(dataSourceSQLExtra,sql,params); // still good
        if (rowAffected > 0) {
            return rowAffected;
        } else {
            return -1;
        }
    }


    public int selectCountRowsByIdMoreThan(Integer id) {
        String sql = "select count(*) from A_APP.USERS_DETAIL where ID >= ?;";
        int rowAffected = jdbcHelper.executeSQLStatementForObject(Integer.class,sql,id);
        if (rowAffected > 0) {
            return rowAffected;
        } else {
            return -1;
        }
    }


    public Integer selectCountRowsByAge(Integer age) {
        String sql = "select count(*) from A_APP.USERS_DETAIL where AGE = ?;";
        return jdbcHelper.executeSQLStatementForObject(Integer.class,sql , List.of(age));
    }


    // How good it very is!!!
    public UserDetail selectById(Integer id) {
        String sql = "select * from A_APP.USERS_DETAIL where ID = ?;";
        return jdbcHelper.executeSQLStatementForObject(sql, this , id);
    }


    public Map<String,String> selectByIdAsObject(Integer id) {
        String sql = "select * from A_APP.USERS_DETAIL where ID = ?;";
        return jdbcHelper.executeSQLStatementForObject(sql,List.of(id));
    }


    public List<UserDetail> selectAll() {
        String sql = "select * from A_APP.USERS_DETAIL;";
        // it is very smart
        // behind the sense do as loop
        // and map to array list
        return jdbcHelper.executeSQLStatementForObject(sql, this);
    }

    public List<Map<String,String>> selectAllAsObject() {
        String sql = "select * from A_APP.USERS_DETAIL;";
        // it is very smart
        // behind the sense do as loop
        // and map to array list
        return jdbcHelper.executeSQLStatementForObject(sql);
    }


    public List<UserDetail> selectAllAsObjectThenConvertToList() {
        String sql = "select * from A_APP.USERS_DETAIL;";
        List<Map<String,String>> data = jdbcHelper.executeSQLStatementForObject(sql);
        List<UserDetail> userDetails = new ArrayList<>();
        for (Map<String,String> map : data) {
            UserDetail userDetail = new UserDetail();
            userDetail.id = Integer.parseInt(map.get("ID"));
            userDetail.firstname = map.get("FIRSTNAME");
            userDetail.lastname = map.get("LASTNAME");
            userDetail.age = Integer.parseInt(map.get("AGE"));
            userDetail.email = map.get("EMAIL");
            userDetail.phone = map.get("PHONE");
            userDetails.add(userDetail);
        }
        return userDetails;
    }

    @Override
    public UserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetail userDetail = new UserDetail();
        userDetail.id = rs.getInt("id");
        userDetail.firstname = rs.getString("firstname");
        userDetail.lastname = rs.getString("lastname");
        userDetail.age = rs.getInt("age");
        userDetail.email = rs.getString("email");
        userDetail.phone = rs.getString("phone");
        log.info("user {}",userDetail);
        return userDetail;
    }

}
