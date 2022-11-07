package com.example.datafromvk.service;

import com.example.datafromvk.model.Role;
import com.example.datafromvk.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {
    private RoleRepository roleRepository;
    public Role getByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
