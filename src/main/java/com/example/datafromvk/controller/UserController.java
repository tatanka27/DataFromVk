package com.example.datafromvk.controller;

import com.example.datafromvk.model.User;
import com.example.datafromvk.model.request.RegisterRequest;
import com.example.datafromvk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private UserService userService;

    @PostMapping("/user/registration")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "registration")
    public void create(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.create(registerRequest.getUsername(), registerRequest.getPassword());
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getAll();
    }
}
