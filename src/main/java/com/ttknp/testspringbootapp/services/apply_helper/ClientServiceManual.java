package com.ttknp.testspringbootapp.services.apply_helper;

import com.ttknp.testspringbootapp.entities.apply_helper.Client;
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
public class ClientServiceManual implements RowMapper<Client> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientServiceManual(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Client> getAllModels() {
        String sql = "select * from TTKNP.clients;";
        return jdbcTemplate.query(sql, this);
    }


    public List<Client> getAllModelsByTableName(String tableName) {
        String sql = "select * from TTKNP."+tableName+";";
        return jdbcTemplate.query(sql, this);
    }


    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.id = rs.getInt("id");
        client.fullName = rs.getString("fullname");
        client.age = rs.getShort("age");
        return client;
    }


}
