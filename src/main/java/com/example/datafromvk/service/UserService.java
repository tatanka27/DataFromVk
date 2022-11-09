package com.example.datafromvk.service;

import com.example.datafromvk.exception.ConflictDataException;
import com.example.datafromvk.model.Role;
import com.example.datafromvk.model.User;
import com.example.datafromvk.model.enumerated.RoleName;
import com.example.datafromvk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public User create(String username, String password) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByName(RoleName.USER.name()));

        return create(username, password, roles);
    }

    public User create(String username, String password, Set<Role> roles) {
        User user = new User(username, passwordEncoder.encode(password), roles);
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictDataException("You cannot use " + username);
        }

    }
}
