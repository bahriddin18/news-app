package com.example.newsapp.service;

import com.example.newsapp.entity.Role;
import com.example.newsapp.exceptions.RescuersNotFoundEx;
import com.example.newsapp.payload.ApiResponse;
import com.example.newsapp.payload.RoleDTO;
import com.example.newsapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    final RoleRepository roleRepository;

    public ApiResponse addRole(RoleDTO roleDTO) {
        boolean b = roleRepository.existsByName(roleDTO.getName());
        if (b) return new ApiResponse("Role already exist", false);
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setPermissions(roleDTO.getPermissions());
        role.setDescription(roleDTO.getDescription());
        roleRepository.save(role);
        return new ApiResponse("Role saved", true);
    }


    public ApiResponse editRole(Long id, RoleDTO roleDTO) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) return new ApiResponse("User not found", false);
        Role role = optionalRole.get();
        role.setDescription(roleDTO.getDescription());
        role.setName(roleDTO.getName());
        role.setPermissions(roleDTO.getPermissions());
        roleRepository.save(role);
        return new ApiResponse("Edited", true);
    }

    public ApiResponse delete(Long id) {
        boolean b = roleRepository.existsById(id);
        if (!b) return new ApiResponse("Role not found", false);
        roleRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }

    public ApiResponse getById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.map(role -> new ApiResponse("Found", true, role)).orElseThrow(() -> new RescuersNotFoundEx("Role", "id", id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("Found", true, roleRepository.findAll());
    }

}
