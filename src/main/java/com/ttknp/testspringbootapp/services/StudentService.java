package com.ttknp.testspringbootapp.services;

import com.fasterxml.jackson.databind.annotation.NoClass;
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
// ** @Service
public class StudentService implements RowMapper<ModelCommon<Integer,String,Short, NoClass,NoClass,NoClass>> {

    private JdbcTemplate jdbcTemplateMySQL;
    private List<ModelCommon<Integer,String,Short, NoClass,NoClass,NoClass>> students;

    // ** @Autowired
    // custom connect dbs with java code
    public StudentService(@Qualifier("dataSourceMySQL") DataSource dataSourceSQL) {
        this.jdbcTemplateMySQL = new JdbcTemplate(dataSourceSQL);
        students = new ArrayList<>();
    }

    // generic
    public List< ModelCommon<Integer,String,Short, NoClass,NoClass,NoClass> >  getAllStudents() {
        String sql = "select * from TTKNP.students;";
        jdbcTemplateMySQL.query(sql,this); // async method
        return students;
    }

    @Override
    public ModelCommon<Integer, String, Short, NoClass, NoClass,NoClass> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ModelCommon<Integer,String,Short, NoClass,NoClass,NoClass> student = new ModelCommon<Integer,String,Short, NoClass,NoClass,NoClass>(
                rs.getInt("id"),
                rs.getString("fullname"),
                rs.getShort("age") ,
                null,
                null,
                null
        );
        students.add(student);
        return student;
    }
}
