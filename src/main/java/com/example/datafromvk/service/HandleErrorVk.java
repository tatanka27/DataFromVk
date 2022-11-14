package com.example.datafromvk.service;

import com.example.datafromvk.exception.VkException;
import com.example.datafromvk.model.dto.ErrorResponseVk;
import com.example.datafromvk.model.dto.ErrorVk;
import org.springframework.stereotype.Component;

@Component
public class HandleErrorVk {
    public void handleError(ErrorResponseVk error) {

        ErrorVk errorVk = error.getError();

        switch (errorVk.getErrorCode()) {
            case "3" -> throw new VkException("vk.com: method not found");
            case "5" -> throw new VkException("vk.com: access_token invalid or not passed");
            case "8" -> throw new VkException("vk.com: invalid request");
            case "15", "100" -> throw new VkException("vk.com: " + errorVk.getErrorMsg());
            case "28" -> throw new VkException(String.format("vk.com: method=%s is unavailable",
                    errorVk.getRequestParams().get(2).getValue()));
            case "38" -> throw new VkException("vk.com: " + "no application for access_token");
            default -> throw new VkException("vk.com: unknown error");
        }
    }
}
