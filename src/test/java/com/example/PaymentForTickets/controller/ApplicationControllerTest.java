package com.example.PaymentForTickets.controller;

import com.example.PaymentForTickets.domain.Application;
import com.example.PaymentForTickets.repository.ApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationRepository mockRepository;


    @Test
    public void whenCreateValidApplication() throws Exception {

        mockMvc.perform(get("/application/564354?date=2020-12-29 23:05:35")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PROCESSED")))
                .andExpect(jsonPath("$.routeNumber", is(564354)))
                .andExpect(jsonPath("$.dataTime", is("2020-12-29 23:05:35")));

    }
    @Test
    public void whenCreatingApplicationWithoutParameters() throws Exception {
        String applicationJson = "{\"routeNumber\":\"100\", \"dataTime\":\"\"}";
        mockMvc.perform(post("/application")
                .content(applicationJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.errors", hasItem("must not be null")))
                .andExpect(jsonPath("$.errors", hasItem("must be greater than or equal to 1000")));

    }


}