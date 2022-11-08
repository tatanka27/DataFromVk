package com.example.datafromvk.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;

    public ErrorResponse(@NonNull HttpStatus status, @NonNull String message) {
        this.code = status.value();
        this.message = message;
    }
}