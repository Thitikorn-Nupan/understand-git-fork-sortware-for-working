package com.ttknp.testspringbootapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestSpringBootAppApplication {

    private static final Logger log = LoggerFactory.getLogger(TestSpringBootAppApplication.class);

    /**
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String hello() {
        log.info("http://localhost:8080 is requested");
        return "Hello World";
    }*/


    @GetMapping(value = {"/hello-world", "hello"})
    public ResponseEntity hello() {
        log.info("http://localhost:8080/hello-world or /hello is requested");
        return new ResponseEntity("Hello World", HttpStatus.ACCEPTED);
    }


    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootAppApplication.class, args);
    }

}
