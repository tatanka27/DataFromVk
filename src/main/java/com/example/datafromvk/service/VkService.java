package com.example.datafromvk.service;

import com.example.datafromvk.exception.BadVkServiceTokenException;
import com.example.datafromvk.exception.NotFoundUserVkException;
import com.example.datafromvk.model.dto.GroupVkResponse;
import com.example.datafromvk.model.dto.UserVk;
import com.example.datafromvk.model.dto.UserVkResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VkService {
    @Value("${app.vk_url}")
    private String vkUrl;
    @Value("${app.vk_v}")
    private String vkV;

    private final String GET_USER_URL = "%s/method/users.get?user_ids=%s&access_token=%s&v=%s";
    private final String IS_MEMBER_URL = "%s/method/groups.isMember?user_id=%d&gropup_id=%s&access_token=%s&v=%s";

    public UserVk getFio(String vkServiceToken, String userId) {
        WebClient webClient = WebClient.create(String.format(GET_USER_URL, vkUrl, userId, vkServiceToken, vkV));
        UserVkResponse response = webClient.get()
                .retrieve()
                .bodyToMono(UserVkResponse.class)
                .block();

        if (response == null || response.getResponse() == null) {
            throw new BadVkServiceTokenException("Bad vk_service_token");
        }
        if (response.getResponse().isEmpty()) {
            throw new NotFoundUserVkException(String.format("User with id=%s not found", userId));
        }

        return response.getResponse().get(0);
    }

    public boolean isGroupMember(String vkServiceToken, int userId, String groupId) {
        WebClient webClient = WebClient.create(String.format(IS_MEMBER_URL, vkUrl, userId, groupId, vkServiceToken, vkV));

        GroupVkResponse response = webClient.get()
                .retrieve()
                .bodyToMono(GroupVkResponse.class)
                .block();
        if (response == null) {
            throw new BadVkServiceTokenException("Bad vk_service_token");
        }

        return response.getResponse() == 1;
    }
}
