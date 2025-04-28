package com.ttknp.testspringbootapp.services;

import com.ttknp.testspringbootapp.entities.UserDetail;
import com.ttknp.testspringbootapp.services.common.ModelServiceCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Slf4j
@Service
public class UserDetailServiceCommon extends ModelServiceCommon<UserDetail> {

    private final JdbcTemplate jdbcTemplateSQL;
    private List<UserDetail> userDetails;

    @Autowired
    public UserDetailServiceCommon(@Qualifier("dataSourceSQL") DataSource dataSourceSQL) {
        this.jdbcTemplateSQL = new JdbcTemplate(dataSourceSQL);
        userDetails = new ArrayList<>();
    }

    @Override
    public List<UserDetail> getAllModels() {
        userDetails.clear();
        // this.loadScript("reload-sql-user-details-table.sql",jdbcTemplateSQL.getDataSource());
        try {
            loadScriptAndPassParamsForGetAllModels("reload-sql-user-details-table-params.sql");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String sql = "select * from TTKNP.A_APP.USERS_DETAIL;";
        jdbcTemplateSQL.query(sql, this); // async method
        return userDetails;
    }

    @Override
    public <U,U2> List<UserDetail> getAllModelsSortBy(U modelKey , U2 modelKey2)  {
        userDetails.clear();
        // run this script before called this method
        this.loadScript("reload-sql-user-details-table.sql",jdbcTemplateSQL.getDataSource());
        String sql = "select * from A_APP.USERS_DETAIL order by %s %s;".formatted(modelKey, modelKey2);
        log.info(sql);
        jdbcTemplateSQL.query(sql, this); // async method
        return userDetails;
    }

    @Override
    public <U> void removeModelByPk(U modelPk) {
        log.info("Removing model with id {}", modelPk);
        // just for testing it's bad logic
        try {
            loadScriptAndPassParamsForRemoveModelByPk("sql-user-details-table-backup.sql",modelPk);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String sql = "delete from A_APP.USERS_DETAIL where ID = ?;";
        int rowAffected = jdbcTemplateSQL.update(sql, modelPk); // async method
        if (rowAffected > 0) {
            log.debug("Successfully removed and backup model with id {}", modelPk);
        } else {
            log.debug("Failed to remove model with id {}", modelPk);
        }
    }


    @Override
    public <U, U2, U3> void removeModelBy3Pk(U modelPk, U2 modelPk2, U3 modelPk3) {
        // 1, 'Alex', 'Ryder', : id,firstname , lastname
        log.info("Removing model with id {}", modelPk);
        String sql = "delete from A_APP.USERS_DETAIL where ID = ? AND firstname = ? AND lastname = ?;";
        int rowAffected = jdbcTemplateSQL.update(sql, modelPk,modelPk2,modelPk3); // async method
        if (rowAffected > 0) {
            log.debug("Successfully removed model with id {} firstname {} , lastname {}", modelPk,modelPk2,modelPk3);
        } else {
            log.debug("Failed to remove model with id {} firstname {} , lastname {}", modelPk,modelPk2,modelPk3);
        }
    }

    @Override
    public <U> void removeModelByManyPkManyType(U... modelPk) {
        for (U modelPkHold : modelPk) {
            log.info("Removing model with modelPkHold {}", modelPkHold);
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


    private void loadScriptAndPassParamsForGetAllModels(String fileName) throws IOException, SQLException {
        HashMap<String,String> params = new HashMap();
        params.put("{ID}","3");
        params.put("{FIRSTNAME}","'Alex'");
        params.put("{LASTNAME}","'Slider'");
        params.put("{EMAIL}","'Alex@gmail.com'");
        params.put("{AGE}","23");
        params.put("{PHONE}","'0923439123'");
        this.loadScript(this.getClass(),fileName,jdbcTemplateSQL,params);
    }

    // if you can not know a type you can specify type param as generic
    // <U> void/Type methodName (U key) {}
    private <U> void loadScriptAndPassParamsForRemoveModelByPk(String fileName,U modelPk) throws IOException, SQLException {
        HashMap<String,String> params = new HashMap();
        params.put("[EMAIL]","email"); // can be null
        params.put("{ID}", String.valueOf(modelPk));
        this.loadScript(this.getClass(),fileName,jdbcTemplateSQL,params);
    }
}
