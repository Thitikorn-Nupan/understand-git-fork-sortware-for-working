package com.ttknp.testspringbootapp;

import com.ttknp.testspringbootapp.repositories.StudentRepo;
import com.ttknp.testspringbootapp.repositories.UserDetailRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


@Slf4j // config logback by using annotation
@SpringBootApplication
@RestController
@Component
@Configuration
public class TestSpringBootAppApplication implements CommandLineRunner {

    private JdbcTemplate jdbcTemplateSQL;
    private JdbcTemplate jdbcTemplateMySQL;
    private UserDetailRepo userDetailRepo;
    private StudentRepo studentRepo;

    @Autowired // custom connect with java code
    // multiple databases
    public TestSpringBootAppApplication(@Qualifier("dataSourceSQL2") DataSource dataSourceSQL,
                                        @Qualifier("dataSourceMySQL") DataSource dataSourceMySQL,
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


    @GetMapping(value = {"/hello-world", "/hello", "/", ""})
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
        @Override public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.debug("(SQL) ID : {}", rs.getInt("ID"));
        return null;
        }
        });
         sql = "select * from TTKNP.students;";
         jdbcTemplateMySQL.query(sql, new RowMapper<Object>() {
        @Override public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.debug("(MySQL) ID : {}", rs.getInt("id"));
        return null;
        }
        });
         */
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

    // this way for config bean on xml by java code without @ImportResource() annotation
    protected static void startSpringBootWithoutImportResourceAnnotation(Class<?> theClass, String... theArgs) {
        log.info("begin : method main");
        ConfigurableEnvironment configurableEnvironment = null;
        Set<String> pathBeanOnXml = new HashSet<>();
        // set the bean paths
        pathBeanOnXml.add("file:D:/my_apps/git-fork-apps/understand-git-fork/test-spring-boot-app-for-working/src/main/resources/xml/spring-context-mysql-database.xml");
        pathBeanOnXml.add("file:D:/my_apps/git-fork-apps/understand-git-fork/test-spring-boot-app-for-working/src/main/resources/xml/spring-context-h2-database.xml");
        // start app
        SpringApplication application = new SpringApplication(TestSpringBootAppApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        // set resource paths to app
        application.setSources(pathBeanOnXml);
        // get env from app
        configurableEnvironment = application.run(theArgs).getEnvironment();
        // testting
        String visualName = ManagementFactory.getRuntimeMXBean().getName();
        // 5548@GA003892-IN0001
        log.debug("env exists {}", visualName);
        // [MapPropertySource@408012754 {name='server.ports', properties={local.server.port=8080}}, ConfigurationPropertySourcesPropertySource@68808938 {name='configurationProperties', properties=org.springframework.boot.context.properties.source.SpringConfigurationPropertySources@2d47b06}, StubPropertySource@1496780902 {name='servletConfigInitParams', properties=java.lang.Object@48106381}, ServletContextPropertySource@63259849 {name='servletContextInitParams', properties=org.apache.catalina.core.ApplicationContextFacade@22e813fc}, PropertiesPropertySource@1128109031 {name='systemProperties', properties={java.specification.version=17, sun.cpu.isalist=amd64, sun.jnu.encoding=MS874, java.class.path=D:\my_apps\git-fork-apps\\understand-git-fork\test-spring-boot-app-for-working\target\classes;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-starter-web\3.4.3\spring-boot-starter-web-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-starter\3.4.3\spring-boot-starter-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot\3.4.3\spring-boot-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-autoconfigure\3.4.3\spring-boot-autoconfigure-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-starter-logging\3.4.3\spring-boot-starter-logging-3.4.3.jar;C:\Users\Admin\.m2\repository\ch\qos\logback\logback-classic\1.5.16\logback-classic-1.5.16.jar;C:\Users\Admin\.m2\repository\ch\qos\logback\logback-core\1.5.16\logback-core-1.5.16.jar;C:\Users\Admin\.m2\repository\org\apache\logging\log4j\log4j-to-slf4j\2.24.3\log4j-to-slf4j-2.24.3.jar;C:\Users\Admin\.m2\repository\org\apache\logging\log4j\log4j-api\2.24.3\log4j-api-2.24.3.jar;C:\Users\Admin\.m2\repository\org\slf4j\jul-to-slf4j\2.0.16\jul-to-slf4j-2.0.16.jar;C:\Users\Admin\.m2\repository\jakarta\annotation\jakarta.annotation-api\2.1.1\jakarta.annotation-api-2.1.1.jar;C:\Users\Admin\.m2\repository\org\yaml\snakeyaml\2.3\snakeyaml-2.3.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-starter-json\3.4.3\spring-boot-starter-json-3.4.3.jar;C:\Users\Admin\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.18.2\jackson-databind-2.18.2.jar;C:\Users\Admin\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.18.2\jackson-annotations-2.18.2.jar;C:\Users\Admin\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.18.2\jackson-core-2.18.2.jar;C:\Users\Admin\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.18.2\jackson-datatype-jdk8-2.18.2.jar;C:\Users\Admin\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.18.2\jackson-datatype-jsr310-2.18.2.jar;C:\Users\Admin\.m2\repository\com\fasterxml\jackson\module\jackson-module-parameter-names\2.18.2\jackson-module-parameter-names-2.18.2.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-starter-tomcat\3.4.3\spring-boot-starter-tomcat-3.4.3.jar;C:\Users\Admin\.m2\repository\org\apache\tomcat\embed\tomcat-embed-core\10.1.36\tomcat-embed-core-10.1.36.jar;C:\Users\Admin\.m2\repository\org\apache\tomcat\embed\tomcat-embed-el\10.1.36\tomcat-embed-el-10.1.36.jar;C:\Users\Admin\.m2\repository\org\apache\tomcat\embed\tomcat-embed-websocket\10.1.36\tomcat-embed-websocket-10.1.36.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-web\6.2.3\spring-web-6.2.3.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-beans\6.2.3\spring-beans-6.2.3.jar;C:\Users\Admin\.m2\repository\io\micrometer\micrometer-observation\1.14.4\micrometer-observation-1.14.4.jar;C:\Users\Admin\.m2\repository\io\micrometer\micrometer-commons\1.14.4\micrometer-commons-1.14.4.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-webmvc\6.2.3\spring-webmvc-6.2.3.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-aop\6.2.3\spring-aop-6.2.3.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-context\6.2.3\spring-context-6.2.3.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-expression\6.2.3\spring-expression-6.2.3.jar;C:\Users\Admin\.m2\repository\org\slf4j\slf4j-api\2.0.16\slf4j-api-2.0.16.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-core\6.2.3\spring-core-6.2.3.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-jcl\6.2.3\spring-jcl-6.2.3.jar;C:\Users\Admin\.m2\repository\org\projectlombok\lombok\1.18.36\lombok-1.18.36.jar;C:\Users\Admin\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\12.8.1.jre11\mssql-jdbc-12.8.1.jre11.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-starter-data-jdbc\3.4.3\spring-boot-starter-data-jdbc-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\boot\spring-boot-starter-jdbc\3.4.3\spring-boot-starter-jdbc-3.4.3.jar;C:\Users\Admin\.m2\repository\com\zaxxer\HikariCP\5.1.0\HikariCP-5.1.0.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-jdbc\6.2.3\spring-jdbc-6.2.3.jar;C:\Users\Admin\.m2\repository\org\springframework\data\spring-data-jdbc\3.4.3\spring-data-jdbc-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\data\spring-data-relational\3.4.3\spring-data-relational-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\data\spring-data-commons\3.4.3\spring-data-commons-3.4.3.jar;C:\Users\Admin\.m2\repository\org\springframework\spring-tx\6.2.3\spring-tx-6.2.3.jar;C:\Users\Admin\.m2\repository\com\mysql\mysql-connector-j\9.1.0\mysql-connector-j-9.1.0.jar, java.vm.vendor=Oracle Corporation, sun.arch.data.model=64, user.variant=, java.vendor.url=https://java.oracle.com/, catalina.useNaming=false, user.timezone=Asia/Bangkok, java.vm.specification.version=17, os.name=Windows 11, APPLICATION_NAME=test-spring-boot-app-for-working, sun.java.launcher=SUN_STANDARD, user.country=US, sun.boot.library.path=C:\Program Files\Java\jdk-17\bin, spring.application.admin.enabled=true, sun.java.command=com.ttknp.testspringbootapp.TestSpringBootAppApplication, com.sun.management.jmxremote=, jdk.debug=release, spring.liveBeansView.mbeanDomain=, sun.cpu.endian=little, user.home=C:\Users\Admin, user.language=en, java.specification.vendor=Oracle Corporation, java.version.date=2024-07-16, java.home=C:\Program Files\Java\jdk-17, spring.output.ansi.enabled=always, file.separator=\, java.vm.compressedOopsMode=Zero based, line.separator=
        //, java.vm.specification.vendor=Oracle Corporation, java.specification.name=Java Platform API Specification, FILE_LOG_CHARSET=UTF-8, java.awt.headless=true, user.script=, sun.management.compiler=HotSpot 64-Bit Tiered Compilers, java.runtime.version=17.0.12+8-LTS-286, user.name=IN0001, spring.jmx.enabled=true, path.separator=;, management.endpoints.jmx.exposure.include=*, os.version=10.0, java.runtime.name=Java(TM) SE Runtime Environment, file.encoding=UTF-8, java.vm.name=Java HotSpot(TM) 64-Bit Server VM, java.vendor.url.bug=https://bugreport.java.com/bugreport/, java.io.tmpdir=C:\Users\Admin\AppData\Local\Temp\, catalina.home=C:\Users\Admin\AppData\Local\Temp\tomcat.8080.8398497531185217949, java.version=17.0.12, user.dir=D:\my_apps\git-fork-apps\\understand-git-fork\test-spring-boot-app-for-working, os.arch=amd64, java.vm.specification.name=Java Virtual Machine Specification, PID=5548, sun.os.patch.level=, CONSOLE_LOG_CHARSET=UTF-8, catalina.base=C:\Users\Admin\AppData\Local\Temp\tomcat.8080.8398497531185217949, native.encoding=MS874, java.library.path=C:\Program Files\Java\jdk-17\bin;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;C:\Program Files\Common Files\Oracle\Java\javapath;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files (x86)\NVIDIA Corporation\PhysX\Common;C:\Program Files\Intel\WiFi\bin\;C:\Program Files\Common Files\Intel\WirelessCommon\;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\WINDOWS\System32\OpenSSH\;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Git\bin;C:\Program Files\Java\jdk-17\bin;C:\Users\Admin\AppData\Local\nvm;C:\nvm4w\nodejs;C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.4\bin;C:\Program Files\JetBrains\WebStorm 2024.3.4\bin;C:\Program Files (x86)\Microsoft SQL Server\160\Tools\Binn\;C:\Program Files\Microsoft SQL Server\160\Tools\Binn\;C:\Program Files\Microsoft SQL Server\Client SDK\ODBC\170\Tools\Binn\;C:\Program Files\Microsoft SQL Server\160\DTS\Binn\;C:\Program Files (x86)\Microsoft SQL Server\160\DTS\Binn\;C:\Program Files (x86)\apache-maven-3.9.9\bin;C:\Users\Admin\AppData\Local\Microsoft\WindowsApps;C:\Program Files\Intel\WiFi\bin\;C:\Program Files\Common Files\Intel\WirelessCommon\;C:\Users\Admin\AppData\Local\Programs\Microsoft VS Code\bin;C:\Users\Admin\AppData\Local\nvm;C:\nvm4w\nodejs;;., java.vm.info=mixed mode, emulated-client, sharing, java.vendor=Oracle Corporation, java.vm.version=17.0.12+8-LTS-286, java.rmi.server.randomIDs=true, sun.io.unicode.encoding=UnicodeLittle, java.class.version=61.0, LOGGED_APPLICATION_NAME=[test-spring-boot-app-for-working] }}, OriginAwareSystemEnvironmentPropertySource@142273642 {name='systemEnvironment', properties={USERDOMAIN_ROAMINGPROFILE=GA003892-IN0001, OneDriveCommercial=C:\Users\Admin\OneDrive - G-ABLE COMPANY LIMITED, LOCALAPPDATA=C:\Users\Admin\AppData\Local, NVM_SYMLINK=C:\nvm4w\nodejs, PROCESSOR_LEVEL=6, USERDOMAIN=GA003892-IN0001, FPS_BROWSER_APP_PROFILE_STRING=Internet Explorer, LOGONSERVER=\\GA003892-IN0001, SESSIONNAME=Console, ALLUSERSPROFILE=C:\ProgramData, PROCESSOR_ARCHITECTURE=AMD64, PSModulePath=C:\Program Files\WindowsPowerShell\Modules;C:\WINDOWS\system32\WindowsPowerShell\v1.0\Modules;C:\Program Files (x86)\Microsoft Azure Information Protection\Powershell;C:\Program Files (x86)\Microsoft SQL Server\160\Tools\PowerShell\Modules\, SystemDrive=C:, OneDrive=C:\Users\Admin\OneDrive - G-ABLE COMPANY LIMITED, APPDATA=C:\Users\Admin\AppData\Roaming, USERNAME=IN0001, ProgramFiles(x86)=C:\Program Files (x86), CommonProgramFiles=C:\Program Files\Common Files, Path=C:\Program Files\Common Files\Oracle\Java\javapath;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files (x86)\NVIDIA Corporation\PhysX\Common;C:\Program Files\Intel\WiFi\bin\;C:\Program Files\Common Files\Intel\WirelessCommon\;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\WINDOWS\System32\OpenSSH\;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Git\bin;C:\Program Files\Java\jdk-17\bin;C:\Users\Admin\AppData\Local\nvm;C:\nvm4w\nodejs;C:\Program Files\JetBrains\IntelliJ IDEA 2024.3.4\bin;C:\Program Files\JetBrains\WebStorm 2024.3.4\bin;C:\Program Files (x86)\Microsoft SQL Server\160\Tools\Binn\;C:\Program Files\Microsoft SQL Server\160\Tools\Binn\;C:\Program Files\Microsoft SQL Server\Client SDK\ODBC\170\Tools\Binn\;C:\Program Files\Microsoft SQL Server\160\DTS\Binn\;C:\Program Files (x86)\Microsoft SQL Server\160\DTS\Binn\;C:\Program Files (x86)\apache-maven-3.9.9\bin;C:\Users\Admin\AppData\Local\Microsoft\WindowsApps;C:\Program Files\Intel\WiFi\bin\;C:\Program Files\Common Files\Intel\WirelessCommon\;C:\Users\Admin\AppData\Local\Programs\Microsoft VS Code\bin;C:\Users\Admin\AppData\Local\nvm;C:\nvm4w\nodejs;, FPS_BROWSER_USER_PROFILE_STRING=Default, EFC_21424=1, PATHEXT=.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC, DriverData=C:\Windows\System32\Drivers\DriverData, OS=Windows_NT, COMPUTERNAME=GA003892-IN0001, NVM_HOME=C:\Users\Admin\AppData\Local\nvm, PROCESSOR_REVISION=8e0a, CommonProgramW6432=C:\Program Files\Common Files, ComSpec=C:\WINDOWS\system32\cmd.exe, ProgramData=C:\ProgramData, ProgramW6432=C:\Program Files, __PSLockDownPolicy=0, HOMEPATH=\Users\Admin, SystemRoot=C:\WINDOWS, TEMP=C:\Users\Admin\AppData\Local\Temp, HOMEDRIVE=C:, PROCESSOR_IDENTIFIER=Intel64 Family 6 Model 142 Stepping 10, GenuineIntel, USERPROFILE=C:\Users\Admin, TMP=C:\Users\Admin\AppData\Local\Temp, CommonProgramFiles(x86)=C:\Program Files (x86)\Common Files, ProgramFiles=C:\Program Files, PUBLIC=C:\Users\Public, NUMBER_OF_PROCESSORS=8, windir=C:\WINDOWS, =::=::\}}, RandomValuePropertySource@556798624 {name='random', properties=java.util.Random@43a59289}, OriginTrackedMapPropertySource@1814594481 {name='Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'', properties={spring.application.name=test-spring-boot-app-for-working, logging.level.root[0]=info, logging.level.root[1]=debug, spring.jpa.hibernate.ddl-auto=none}}, ApplicationInfoPropertySource@862862306 {name='applicationInfo', properties={spring.application.pid=5548}}]
        log.debug("env exists {}", configurableEnvironment.getPropertySources()); //
        log.debug("env exists {}", configurableEnvironment.getProperty("spring.application.name")); // test-spring-boot-app-for-working it get value from application.prop
        log.debug("env exists {}", configurableEnvironment.getProperty("server.servlet.context-path")); //
        // {USERDOMAIN_ROAMINGPROFILE=GA003892-IN0001, OneDriveCommercial=C:\Users\Admin\OneDrive - G-ABLE COMPANY LIMITED, LOCALAPPDATA=C:\Users\Admin\AppData\Local, NVM_SYMLINK=C:\nvm4w\nodejs, PROCESSOR_LEVEL=6, USERDOMAIN=GA003892-IN0001,
        log.debug("env exists {}", configurableEnvironment.getSystemEnvironment());
        // java.specification.version=17, sun.cpu.isalist=amd64, sun.jnu.encoding=MS874, java.class.path=D:\my_apps\git-fork-apps\\understand-git-fork\test-spring-boot-app-for-working\target\classes;C:\Users\A
        log.debug("env exists {}", configurableEnvironment.getSystemProperties());
        /*
        String w_rootName = w_env.getProperty("server.servlet.context-path");
        log.info("complete for run : jvm-name(pid@serverName) = {}, rootName = {}, port = {}", w_vmName, w_rootName, w_env.getProperty("server.port"));
        log.info("{}, activeProfile = {}", w_rootName, w_env.getProperty("spring.profiles.active"));
        log.info("{}, pidFile = {}", w_rootName, w_env.getProperty("spring.pid.file"));
        log.info("{}, logPath = {}", w_rootName, w_env.getProperty("insapp.path.logback"));
        log.info("{}, folderTemp for write file = {}", w_rootName, w_env.getProperty("app.io.path.temp"));
        */
    }

    public static void main(String[] args) throws SQLException {
        // Run by auto config have to use @ImportResource() annotation
        // SpringApplication.run(TestSpringBootAppApplication.class, args);
        // Custom runner
        startSpringBootWithoutImportResourceAnnotation(TestSpringBootAppApplication.class, args);
    }

}
