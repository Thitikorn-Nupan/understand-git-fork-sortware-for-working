package com.ttknp.testspringbootapp.services;

import com.ttknp.testspringbootapp.entities.Student;
import com.ttknp.testspringbootapp.entities.UserDetail;
import com.ttknp.testspringbootapp.entities.common.ModelCommon;
import com.ttknp.testspringbootapp.services.common.ModelServiceCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
@Slf4j
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
        this.loadScript("reload-sql-user-details-table.sql",jdbcTemplateSQL.getDataSource());
        String sql = "select * from TTKNP.A_APP.USERS_DETAIL;";
        jdbcTemplateSQL.query(sql, this); // async method
        return userDetails;
    }

    @Override
    public <U> void removeModelByPk(U modelPk) {
        log.info("Removing model with id {}", modelPk);
        String sql = "delete from A_APP.USERS_DETAIL where ID = ?;";
        int rowAffected =jdbcTemplateSQL.update(sql, modelPk); // async method
        if (rowAffected > 0) {
            log.debug("Successfully removed model with id {}", modelPk);
        } else {
            log.debug("Failed to remove model with id {}", modelPk);
        }
    }

    @Override
    public <U> UserDetail removeModelByPkAndAuth(U modelPk) {
        return null;
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
