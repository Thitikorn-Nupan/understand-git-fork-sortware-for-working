package com.ttknp.testspringbootapp.services;

import com.ttknp.testspringbootapp.entities.Student;
import com.ttknp.testspringbootapp.entities.UserDetail;
import com.ttknp.testspringbootapp.entities.common.ModelCommon;
import com.ttknp.testspringbootapp.services.common.ModelServiceCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceCommon extends ModelServiceCommon<UserDetail> {

    private JdbcTemplate jdbcTemplateSQL;
    private List<UserDetail> userDetails;

    @Autowired
    public UserDetailServiceCommon(@Qualifier("dataSourceSQL") DataSource dataSourceSQL) {
        this.jdbcTemplateSQL = new JdbcTemplate(dataSourceSQL);
        userDetails = new ArrayList<>();
    }

    @Override
    public List<UserDetail> getAllModels() {
        String sql = "select * from TTKNP.A_APP.USERS_DETAIL;";
        jdbcTemplateSQL.query(sql, this); // async method
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
        userDetails.add(userDetail);
        return userDetail;
    }
}
