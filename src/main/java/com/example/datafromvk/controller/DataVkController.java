package com.example.datafromvk.controller;

import com.example.datafromvk.model.request.UserVkRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class DataVkController {
    @GetMapping("user_vk")
    @Operation(summary = "userVK")
    public String getUserVk(@Valid @RequestBody UserVkRequest request) {
        return request.getUserId();
    }
}
