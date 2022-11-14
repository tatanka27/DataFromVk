package com.example.datafromvk.controller;

import com.example.datafromvk.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/.env"})
public class DataVkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    private final String loginJson = "{\"username\":\"%s\", \"password\": \"%s\"}";

    private final String userVkJson = "{\"user_id\":\"%s\", \"group_id\": \"%s\"}";

    private final String vkServiceToken;

    private String jwtToken;

    public DataVkControllerTest(@Value("${vk-access-token}") String vkServiceToken) {
        this.vkServiceToken = vkServiceToken;
    }

    @BeforeEach
    void init() throws Exception {
        userRepository.deleteAll();
        String username = "test@test.ru";
        String password = "123456";
        createUser(username, password);
        jwtToken = login(username, password);
    }

    @Test
    void shouldReturnOk_getData() throws Exception {
        mockMvc.perform(get("/user_vk")
                        .content(String.format(userVkJson, "tanka_tatanka", "1"))
                        .header("Content-Type", "application/json")
                        .header("vk_service_token", vkServiceToken)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk()).andReturn();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"user_id\":\"\", \"group_id\":\"123456\"}",
            "{\"user_id\":\"user\", \"group_id\":\"\"}",
            "{\"user\":\"user\", \"group_id\":\"123456\"}",
            "{\"user_id\":\"user\", \"group\":\"123456\"}",
            "{\"\", \"\"}",
            "{}"
    })
    void shouldReturnBadRequest_getData(String json) throws Exception {
        getDataUserVk(json, 400);
    }

    @Test
    void shouldReturnBadRequest_missingToken_getData() throws Exception {
        mockMvc.perform(get("/user_vk")
                        .content(String.format(userVkJson, "user", "1"))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is(400));
    }

    @Test
    void shouldReturnUnauthorized_getData() throws Exception {
        mockMvc.perform(get("/user_vk")
                        .content(String.format(userVkJson, "user", "1"))
                        .header("Content-Type", "application/json")
                        .header("vk_service_token", vkServiceToken))
                .andExpect(status().is(401));
    }

    private void getDataUserVk(String json, int expectedCode) throws Exception {
        mockMvc.perform(get("/user_vk")
                        .content(json)
                        .header("Content-Type", "application/json")
                        .header("vk_service_token", vkServiceToken)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is(expectedCode));
    }

    private void createUser(String username, String password) throws Exception {
        mockMvc.perform(post("/user/registration")
                        .content(String.format(loginJson, username, password))
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());
    }

    private String login(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                        .content(String.format(loginJson, username, password))
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk()).andReturn();

        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }
}
