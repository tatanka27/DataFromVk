package com.example.datafromvk.controller;

import com.example.datafromvk.model.request.RegisterRequest;
import com.example.datafromvk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private UserService userService;

    @PostMapping(path = "/user/registration", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "registration new user")
    public void create(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.create(registerRequest.getUsername(), registerRequest.getPassword());
    }
}
