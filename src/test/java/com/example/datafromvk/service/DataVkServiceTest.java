package com.example.datafromvk.service;

import com.example.datafromvk.exception.VkException;
import com.example.datafromvk.model.dto.UserVk;
import com.example.datafromvk.model.response.DataUserVkResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DataVkServiceTest {

    @Mock
    private VkService vkService;
    @InjectMocks
    private DataVkService dataVkService;

    private final UserVk userVk = new UserVk(123, "lastName", "firstName", "middleName");

    private final String tokenTest = "tokenTest";

    private final String userIdTest = "user";

    private final String groupIdTest = "group";
    DataUserVkResponse vkResponse = new DataUserVkResponse(userVk.getLastName(), userVk.getFirstName(), userVk.getMiddleName(),
            true);

    @Test
    void shouldReturnDataUser() throws JsonProcessingException {
        when(vkService.getFio(anyString(), anyString())).thenReturn(userVk);
        when(vkService.isGroupMember(anyString(), anyInt(), anyString())).thenReturn(true);

        DataUserVkResponse vkResponseTest = dataVkService.getDataUserVk(tokenTest, userIdTest, groupIdTest);

        Assertions.assertEquals(vkResponse.getLastName(), vkResponseTest.getLastName());
        Assertions.assertEquals(vkResponse.getFirstName(), vkResponseTest.getFirstName());
        Assertions.assertEquals(vkResponse.getMiddleName(), vkResponseTest.getMiddleName());
        Assertions.assertEquals(vkResponse.isMember(), vkResponseTest.isMember());
    }

    @Test
    void shouldThrow_ifBadToken() throws JsonProcessingException {

        when(vkService.getFio(anyString(), anyString())).thenThrow(new VkException("vk.com: access_token invalid or not passed"));

        VkException thrown = assertThrows(
                VkException.class,
                () -> dataVkService.getDataUserVk(tokenTest, userIdTest, groupIdTest)
        );

        assertTrue(thrown.getMessage().contains("vk.com: access_token invalid or not passed"));
    }

    @Test
    void shouldThrow_ifUserNotFound() throws JsonProcessingException {

        when(vkService.getFio(anyString(), anyString())).thenThrow(new VkException(String.format("User with id=%s not found", userIdTest)));

        VkException thrown = assertThrows(
                VkException.class,
                () -> dataVkService.getDataUserVk(tokenTest, userIdTest, groupIdTest)
        );

        assertTrue(thrown.getMessage().contains(String.format("User with id=%s not found", userIdTest)));
    }

    @Test
    void shouldThrow_ifGroupAccessDenied() throws JsonProcessingException {

        when(vkService.getFio(anyString(), anyString())).thenReturn(userVk);
        when(vkService.isGroupMember(anyString(), anyInt(), anyString())).thenThrow(new VkException("Access denied"));

        VkException thrown = assertThrows(
                VkException.class,
                () -> dataVkService.getDataUserVk(tokenTest, userIdTest, groupIdTest)
        );

        assertTrue(thrown.getMessage().contains("Access denied"));
    }
}