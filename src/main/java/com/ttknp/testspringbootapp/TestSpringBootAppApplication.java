package com.ttknp.testspringbootapp;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j // config logback by using annotation
@SpringBootApplication
@RestController
public class TestSpringBootAppApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // ** private static final Logger log = LoggerFactory.getLogger(TestSpringBootAppApplication.class);

    /**
     * @GetMapping
     * @ResponseBody
     * @ResponseStatus(HttpStatus.ACCEPTED) public String hello() {
     * log.info("http://localhost:8080 is requested");
     * return "Hello World";
     * }
     */


    @GetMapping(value = {"/hello-world", "/hello","/",""})
    public ResponseEntity hello() {
        log.info("http://localhost:8080/hello-world or /hello is requested");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Hello World");
    }

    @PostMapping(value = "/post")
    public ResponseEntity helloFromPost() {
        log.info("http://localhost:8080/post is requested");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Data", "Hello World")
                .body(null);
    }


    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // ** you have to specify database name on url if table you fecthing it's not default schema
        String sql = "select * from A_APP.USERS_DETAIL;";
        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.debug("ID : {}", rs.getInt("ID"));
                return null;
            }
        });
    }

}
