package com.example.datafromvk.controller;

import com.example.datafromvk.model.request.LoginRequest;
import com.example.datafromvk.security.JwtTokenUtil;
import com.example.datafromvk.security.AuthUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthApiController {
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(authentication);
    }
}
