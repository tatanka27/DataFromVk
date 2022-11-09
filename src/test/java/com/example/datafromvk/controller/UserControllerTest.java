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

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    private final String registerJson = "{\"username\":\"%s\", \"password\": \"%s\"}";

    @BeforeEach
    void init() {
        userRepository.deleteAll();
    }

    @Test
    void shouldReturnOk_registerUser() throws Exception {
        String json = String.format(registerJson, "test@test.ru", randomNumeric(8));
        createUser(json, 200);
    }

    @Test
    void shouldReturnDuplicate_registerUser() throws Exception {
        String json = String.format(registerJson, "test@test.ru", randomNumeric(8));

        for (int expectedCode : new int[]{200, 409}) {
            createUser(json, expectedCode);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"username\":\"test.t\", \"password\":\"12345\"}",
            "{\"username\":\"\", \"password\":\"12345\"}",
            "{\"username\":\"test@test.ru\"}",
            "{,\"password\":\"12345\"}",
            "{\"username\":\"test\", \"password\":\"\"}",
            "{\"name\":\"test\", \"password\":\"12345\"}",
            "{\"username\":\"test@test.ru\", \"pass\":\"12345\"}",
            "{\"\", \"\"}",
            "{}"
    })
    void shouldBadRequest_registerUser(String json) throws Exception {
        createUser(json, 400);
    }

    public void createUser(String json, int expectedCode) throws Exception {
        mockMvc.perform(post("/user/registration")
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().is(expectedCode));
    }
}
