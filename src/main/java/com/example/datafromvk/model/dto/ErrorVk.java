package com.example.datafromvk.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorVk {
    @JsonProperty("error_code")
    String errorCode;

    @JsonProperty("error_msg")
    String errorMsg;

    @JsonProperty("request_params")
    List<RequestParamVk> requestParams;
}
