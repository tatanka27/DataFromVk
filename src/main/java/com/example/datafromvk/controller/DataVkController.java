package com.example.datafromvk.controller;

import com.example.datafromvk.model.request.UserVkRequest;
import com.example.datafromvk.model.response.DataUserVkResponse;
import com.example.datafromvk.service.DataVkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DataVkController {

    private DataVkService dataVkService;

    @GetMapping("/user_vk")
    @Operation(summary = "get by user_id and group_id data from vk")
    public DataUserVkResponse getDataUserVk(@RequestHeader(name = "vk_service_token") String vkServiceToken,
                                            @Valid @RequestBody UserVkRequest request) {
        return dataVkService.getDataUserVk(vkServiceToken, request.getUserId(), request.getGroupId());
    }
}
