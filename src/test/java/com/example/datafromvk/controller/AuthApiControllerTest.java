package com.example.datafromvk.controller;

import com.example.datafromvk.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final String json = "{\"username\":\"%s\", \"password\": \"%s\"}";

    @BeforeEach
    void init() throws Exception {
        userRepository.deleteAll();
        mockMvc.perform(post("/user/registration")
                        .content(String.format(json, "test@test.ru", "123456"))
                        .header("Content-Type", "application/json"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldReturnOk_login() throws Exception {
        String loginJson = String.format(json, "test@test.ru", "123456");
        login(loginJson, 200);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"username\":\"bad@test.ru\", \"password\":\"123456\"}",
    })
    void shouldReturnInvalidCredentials_login(String loginJson) throws Exception {
        login(loginJson, 401);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"username\":\"test.t\", \"password\":\"123456\"}",
            "{\"username\":\"\", \"password\":\"12345\"}",
            "{\"username\":\"test@test.ru\"}",
            "{,\"password\":\"12345\"}",
            "{\"username\":\"test\", \"password\":\"\"}",
            "{\"name\":\"test\", \"password\":\"12345\"}",
            "{\"username\":\"test@test.ru\", \"pass\":\"12345\"}",
            "{\"\", \"\"}",
            "{}"
    })
    void shouldReturnBadRequest_authPost(String loginJson) throws Exception {
        login(loginJson, 400);
    }

    private void login(String json, int expectedCode) throws Exception {
        mockMvc.perform(post("/login")
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().is(expectedCode)).andReturn();
    }
}
