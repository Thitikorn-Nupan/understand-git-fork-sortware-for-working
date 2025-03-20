package com.ttknp.testspringbootapp;

import com.ttknp.testspringbootapp.repositories.StudentRepo;
import com.ttknp.testspringbootapp.repositories.UserDetailRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;


@Slf4j // config logback by using annotation
@SpringBootApplication// (scanBasePackages = {"com.ttknp.testspringbootapp"}) // (exclude = {DataSourceAutoConfiguration.class })
@RestController
@Component
public class TestSpringBootAppApplication implements CommandLineRunner {

    private JdbcTemplate jdbcTemplateSQL;
    private JdbcTemplate jdbcTemplateMySQL;
    private UserDetailRepo userDetailRepo;
    private StudentRepo studentRepo;

    @Autowired // custom connect with java code
    // multiple databases
    public TestSpringBootAppApplication(@Qualifier("dataSourceSQL2") DataSource dataSourceSQL,
                                        @Qualifier("dataSourceMySQL")  DataSource dataSourceMySQL,
                                        UserDetailRepo userDetailRepo,
                                        StudentRepo studentRepo) { // , UserDetailRepo userDetailRepo
        this.jdbcTemplateSQL = new JdbcTemplate(dataSourceSQL);
        this.jdbcTemplateMySQL = new JdbcTemplate(dataSourceMySQL);
        this.userDetailRepo = userDetailRepo;
        this.studentRepo = studentRepo;
    }
    /*
    @Autowired
    public TestSpringBootAppApplication(CustomDriverConfigByXML customDriverConfigByXML) {
        this.customDriverConfigByXML = customDriverConfigByXML;
    }
    */

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


    /*public static void main(String[] args) {
        DataSource dataSource = getDataSourceFromXml();
        // Use the dataSource
        System.out.println(dataSource);
    }*/
    @Override
    public void run(String... args) throws Exception {


        /*
        // work
        userDetailRepo.findAll().forEach((user) -> {
            log.info("User.id: {}" , user.id);
        });
        // not worked
        studentRepo.findAll().forEach((student) -> {
            log.info("Student.id: {}" , student.id);
        });
        */

        // ** you have to specify database name on url if table you work it's not default schema
        /**
         * manual queries
        String sql = "select * from TTKNP.A_APP.USERS_DETAIL;";
        jdbcTemplateSQL.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.debug("(SQL) ID : {}", rs.getInt("ID"));
                return null;
            }
        });
        sql = "select * from TTKNP.students;";
        jdbcTemplateMySQL.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                log.debug("(MySQL) ID : {}", rs.getInt("id"));
                return null;
            }
        });
        */
    }

    // ***
    // get connection by retrieved an DriverManagerDataSource bean on xml *** manual
    // ***
    public static void getDataSourceFromXmlAndTestConfig() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("xml/spring-context.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource2");
        // add fetched schemas on TTKNP database
        log.debug("dataSource.getConnection().getSchema() : {}", dataSource.getConnection().getSchema());
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(TestSpringBootAppApplication.class, args);
    }

}
