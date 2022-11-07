package com.example.datafromvk.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserVkRequest {
    @NotBlank
    @JsonProperty("user_id")
    String userId;

    @NotBlank
    @JsonProperty("group_id")
    String groupId;
}
