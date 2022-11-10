package com.example.datafromvk.service;

import com.example.datafromvk.model.dto.UserVk;
import com.example.datafromvk.model.response.DataUserVkResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataVkService {

    private final VkService vkService;

    public DataUserVkResponse getDataUserVk(String vkServiceToken, String userId, String groupId) throws JsonProcessingException {
        UserVk userVk = vkService.getFio(vkServiceToken, userId);

        boolean isMember = vkService.isGroupMember(vkServiceToken, userVk.getId(), groupId);

        return new DataUserVkResponse(userVk.getLastName(), userVk.getFirstName(), userVk.getMiddleName(), isMember);
    }
}
