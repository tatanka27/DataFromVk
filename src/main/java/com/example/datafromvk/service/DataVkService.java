package com.example.datafromvk.service;

import com.example.datafromvk.exception.VkException;
import com.example.datafromvk.model.dto.GroupVkResponse;
import com.example.datafromvk.model.dto.UserVk;
import com.example.datafromvk.model.dto.UserVkResponse;
import com.example.datafromvk.model.response.DataUserVkResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DataVkService {
    @Value("${app.vk_url}")
    private String vkUrl;

    @Value("${app.vk_v}")
    private String vkV;

    private String vkServiceToken;

    private final String GET_USER_URL = "%s/method/users.get?user_ids=%s&access_token=%s&v=%s";
    private final String IS_MEMBER_URL = "%s/method/groups.isMember?user_id=%d&gropup_id=%s&access_token=%s&v=%s";

    public DataUserVkResponse dataUserVk(String vkServiceToken, String userId, String groupId) {
        this.vkServiceToken = vkServiceToken;
        UserVk userVk = getFio(userId);
        boolean isMember = isGroupMember(userVk.getId(), groupId);
        return new DataUserVkResponse(userVk.getLastName(), userVk.getFirstName(), userVk.getMiddleName(), isMember);
    }

    private UserVk getFio(String userId) {
        WebClient webClient = WebClient.create(String.format(GET_USER_URL, vkUrl, userId, vkServiceToken, vkV));
        UserVkResponse response = webClient.get()
                .retrieve()
                .bodyToMono(UserVkResponse.class)
                .block();

        if (response == null || response.getResponse() == null) {
            throw new VkException("Problem with response from users.get()");
        }
        if (response.getResponse().isEmpty()) {
            throw new VkException(String.format("User with id=%s not found", userId));
        }

        return response.getResponse().get(0);
    }

    private boolean isGroupMember(int userId, String groupId) {
        WebClient webClient = WebClient.create(String.format(IS_MEMBER_URL, vkUrl, userId, groupId, vkServiceToken, vkV));

        GroupVkResponse response = webClient.get()
                .retrieve()
                .bodyToMono(GroupVkResponse.class)
                .block();
        if (response == null) {
            throw new VkException("Problem with response from groups.isMember()");
        }

        return response.getResponse() == 1;
    }
}
