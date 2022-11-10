package com.example.datafromvk.service;

import com.example.datafromvk.exception.VkException;
import com.example.datafromvk.model.dto.ErrorResponseVk;
import com.example.datafromvk.model.dto.GroupVkResponse;
import com.example.datafromvk.model.dto.UserVk;
import com.example.datafromvk.model.dto.UserVkResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class VkService {
    @Value("${app.vk_url}")
    private String vkUrl;

    @Value("${app.vk_v}")
    private String vkV;

    private final ObjectMapper mapper;

    private final HandleErrorVk handleErrorVk;

    private final String GET_USER_URL = "%s/method/users.get?user_ids=%s&access_token=%s&v=%s";

    private final String IS_MEMBER_URL = "%s/method/groups.isMember?user_id=%d&group_id=%s&access_token=%s&v=%s";

    public UserVk getFio(String vkServiceToken, String userId) throws JsonProcessingException {
        WebClient webClient = WebClient.create(String.format(GET_USER_URL, vkUrl, userId, vkServiceToken, vkV));

        String response = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ErrorResponseVk errorResponse = mapper.readValue(response, ErrorResponseVk.class);

        if (errorResponse.getError() != null && errorResponse.getError().getErrorCode() != null) {
            handleErrorVk.handleError(errorResponse);
        }

        UserVkResponse userVkResponse = mapper.readValue(response, UserVkResponse.class);

        if (userVkResponse.getResponse().isEmpty()) {
            throw new VkException(String.format("User with id=%s not found", userId));
        }

        return userVkResponse.getResponse().get(0);
    }

    public boolean isGroupMember(String vkServiceToken, int userId, String groupId) throws JsonProcessingException {
        WebClient webClient = WebClient.create(String.format(IS_MEMBER_URL, vkUrl, userId, groupId, vkServiceToken, vkV));

        String response = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ErrorResponseVk errorResponse = mapper.readValue(response, ErrorResponseVk.class);

        if (errorResponse.getError() != null && errorResponse.getError().getErrorCode() != null) {
            handleErrorVk.handleError(errorResponse);
        }

        GroupVkResponse groupVkResponse = mapper.readValue(response, GroupVkResponse.class);

        return groupVkResponse.getResponse() == 1;
    }
}
