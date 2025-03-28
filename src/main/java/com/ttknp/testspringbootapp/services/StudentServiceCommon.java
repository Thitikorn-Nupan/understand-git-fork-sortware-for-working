package com.ttknp.testspringbootapp.services;

import com.fasterxml.jackson.databind.annotation.NoClass;
import com.ttknp.testspringbootapp.entities.Student;
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
public class StudentServiceCommon extends ModelServiceCommon<Student> {

    private JdbcTemplate jdbcTemplateMySQL;
    private List<Student> students;

    @Autowired
    public StudentServiceCommon(@Qualifier("dataSourceMySQL") DataSource dataSourceSQL) {
        this.jdbcTemplateMySQL = new JdbcTemplate(dataSourceSQL);
        students = new ArrayList<>();
    }

    @Override
    public List<Student> getAllModels() {
        // run this script before called this method
        this.loadScript("reload-mysql-students-table.sql",jdbcTemplateMySQL.getDataSource());
        String sql = "select * from TTKNP.students;";
        jdbcTemplateMySQL.query(sql, this); // async method
        return students;
    }

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.id = rs.getInt("id");
        student.fullName = rs.getString("fullname");
        student.age = rs.getShort("age");
        students.add(student);
        return student;
    }
}
