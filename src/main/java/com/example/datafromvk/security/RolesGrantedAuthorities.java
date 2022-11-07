package com.example.datafromvk.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public class RolesGrantedAuthorities implements GrantedAuthority {

    private final String authority;

}
