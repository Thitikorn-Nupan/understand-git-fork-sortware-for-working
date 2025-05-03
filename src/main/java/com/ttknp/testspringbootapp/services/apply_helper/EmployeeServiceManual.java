package com.ttknp.testspringbootapp.services.apply_helper;

import com.ttknp.testspringbootapp.entities.apply_helper.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class EmployeeServiceManual implements RowMapper<Employee> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeServiceManual(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Employee> getAllModels() {
        String sql = "select * from TTKNP.clients;";
        return jdbcTemplate.query(sql, this);
    }

    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.id = rs.getInt("id");
        employee.fullName = rs.getString("fullname");
        employee.age = rs.getShort("age");
        return employee;
    }


}
