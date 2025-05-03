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


// specify this @Service As @Bean on config class
public class StudentExtraServiceForCustomDriverConfigAndServiceByXML implements RowMapper<Student> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentExtraServiceForCustomDriverConfigAndServiceByXML(DataSource dataSource) {
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
