package com.example.datafromvk.service;

import com.example.datafromvk.model.enumerated.Role;
import com.example.datafromvk.model.User;
import com.example.datafromvk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User create(String username, String password) {
        Set<com.example.datafromvk.model.Role> roles = new HashSet<>();
        roles.add(roleService.getByName(Role.USER.name()));

        return create(username, password, roles);
    }

    public User create(String username, String password, Set<com.example.datafromvk.model.Role> roles) {
        User user = new User(username, passwordEncoder.encode(password), roles);
        return userRepository.save(user);
    }
}
