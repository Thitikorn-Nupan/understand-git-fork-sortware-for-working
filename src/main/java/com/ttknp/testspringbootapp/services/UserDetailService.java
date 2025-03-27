package com.ttknp.testspringbootapp.services;

import com.ttknp.testspringbootapp.entities.common.ModelCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserDetailService implements RowMapper<ModelCommon<Integer,String,String, Integer ,String,String>> {

    private JdbcTemplate jdbcTemplateSQL;
    private List<ModelCommon<Integer,String,String, Integer,String,String>> userDetails;

    @Autowired
    // custom connect dbs with java code
    public UserDetailService(@Qualifier("dataSourceSQL") DataSource dataSourceSQL) {
        this.jdbcTemplateSQL = new JdbcTemplate(dataSourceSQL);
        userDetails = new ArrayList<>();
    }

    // generic
    public List< ModelCommon<Integer,String,String, Integer ,String,String> >  getAllUserDetails() {
        String sql = "select * from TTKNP.A_APP.USERS_DETAIL;";
        jdbcTemplateSQL.query(sql,this); // async method
        return userDetails;
    }

    @Override
    public ModelCommon<Integer,String,String, Integer ,String,String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ModelCommon<Integer,String,String, Integer ,String,String> userDetail = new ModelCommon<Integer,String,String, Integer ,String,String>(
                rs.getInt("id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getInt("age") ,
                rs.getString("email"),
                rs.getString("phone")
        );
        userDetails.add(userDetail);
        return userDetail;
    }
}
