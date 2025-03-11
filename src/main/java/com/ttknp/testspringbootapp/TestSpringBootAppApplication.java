package com.ttknp.testspringbootapp;

import com.ttknp.testspringbootapp.repositories.UserDetailRepo;
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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j // config logback by using annotation
@SpringBootApplication
@RestController
@Component
public class TestSpringBootAppApplication implements CommandLineRunner {

    // @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserDetailRepo userDetailRepo;

    @Autowired // custom connect with java code
    public TestSpringBootAppApplication(DriverManagerDataSource dataSource,UserDetailRepo userDetailRepo) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userDetailRepo = userDetailRepo;
    }

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

    @Override
    public void run(String... args) throws Exception {
        // ** you have to specify database name on url if table you work it's not default schema
        /**
        String sql = "select * from TTKNP.A_APP.USERS_DETAIL;";
        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.debug("ID : {}", rs.getInt("ID"));
                return null;
            }
        });
        */
        userDetailRepo.findAll().forEach((user) -> {
            log.info("User.id: {}" , user.id);
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootAppApplication.class, args);
    }

}
