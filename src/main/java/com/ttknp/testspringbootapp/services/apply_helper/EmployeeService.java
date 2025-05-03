package com.ttknp.testspringbootapp.services.apply_helper;

import com.ttknp.testspringbootapp.entities.apply_helper.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service // if close @Service have to do by manual apply with helper ClientAndEmployeeConfigByXML class
public class EmployeeService implements RowMapper<Employee> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeService(DataSource dataSource) {
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
