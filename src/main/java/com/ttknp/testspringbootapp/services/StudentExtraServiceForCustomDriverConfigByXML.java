package com.ttknp.testspringbootapp.services;

import com.ttknp.testspringbootapp.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// @Service
public class StudentExtraServiceForCustomDriverConfigByXML implements RowMapper<Student> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setStudentExtraServiceJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Student> getAllModels() {
        String sql = "select * from TTKNP.students;";
        return jdbcTemplate.query(sql, this);
    }

    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.id = rs.getInt("id");
        student.fullName = rs.getString("fullname");
        student.age = rs.getShort("age");
        return student;
    }
}
