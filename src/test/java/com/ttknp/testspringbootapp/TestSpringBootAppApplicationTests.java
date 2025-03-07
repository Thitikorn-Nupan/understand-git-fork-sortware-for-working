package com.ttknp.testspringbootapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest
@WebMvcTest(TestSpringBootAppApplication.class)
class TestSpringBootAppApplicationTests {

    //    @Test
    //    void contextLoads() {
    //
    //    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloWorldEndpoint() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/hello-world");

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isAccepted())
                .andExpect(header().stringValues("Content-Type", "text/plain;charset=UTF-8"))
                .andDo(print());
    }


    @Test
    public void testHelloWorldPostEndpoint() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/post");

        // ** ResultActions class to handle the response of the REST API.
        ResultActions response = mockMvc.perform(request);

        // then - verify the output
        response
                .andExpect(status().isAccepted())
                .andExpect(header().stringValues("Data", "Hello World"))
                .andDo(print());
    }

}
