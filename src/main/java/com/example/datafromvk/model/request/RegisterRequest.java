package com.example.datafromvk.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Email
    String username;

    @NotBlank
    @Size(min = 6)
    String password;
}
