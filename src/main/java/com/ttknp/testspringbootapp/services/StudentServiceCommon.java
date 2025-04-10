package com.ttknp.testspringbootapp.services;

import com.fasterxml.jackson.databind.annotation.NoClass;
import com.ttknp.testspringbootapp.entities.Student;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public <U> void removeModelByPk(U modelCode) { // U can be any type
        log.info("Removing model with id {}", modelCode);
        String sql = "delete from TTKNP.students where code = ?;";
        int rowAffected =jdbcTemplateMySQL.update(sql, modelCode); // async method
        if (rowAffected > 0) {
            log.debug("Successfully removed model with code {}", modelCode);
        } else {
            log.debug("Failed to remove model with code {}", modelCode);
        }
    }

    @Override
    public <U> void removeModelByManyPkManyType(U... modelPk) {

    }

    @Override
    public <U, U2, U3> void removeModelBy3Pk(U modelPk, U2 modelPk2, U3 modelPk3) {
        // 'A0002' , 'Bob Johnson' , 22
        String sql = "delete from TTKNP.students where code = ? AND fullname = ? AND age = ?;";
        int rowAffected = jdbcTemplateMySQL.update(sql, modelPk,modelPk2,modelPk3); // async method
        if (rowAffected > 0) {
            log.debug("Successfully removed model with code {} fullname {} age {}", modelPk,modelPk2,modelPk3);
        } else {
            log.debug("Failed to remove model with code {} fullname {} age {}", modelPk,modelPk2,modelPk3);
        }
    }

    @Override
    public <U> Student removeModelByPkAndAuth(U modelPk) {
        return null;
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
