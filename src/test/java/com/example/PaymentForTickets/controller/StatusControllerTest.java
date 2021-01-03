package com.example.PaymentForTickets.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StatusControllerTest {


    private  WebTestClient webTestClient;


    @Test
    public void givenErrorReturn_whenUsernamePresent_thenOk() throws IOException {

        Long aLong = webTestClient.get()
                .uri("/new{id}", 14)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Long.class)
                .getResponseBody()
                .blockFirst();

        assertEquals(14L, aLong);

    }


    @Test
    void getStatus() {
    }

    @Test
    void setStatus() {
    }
}