package com.example.newsapp.service;


import com.example.newsapp.entity.Role;
import com.example.newsapp.entity.User;
import com.example.newsapp.exceptions.RescuersNotFoundEx;
import com.example.newsapp.payload.ApiResponse;
import com.example.newsapp.payload.RegisterDTO;
import com.example.newsapp.payload.UserDTO;
import com.example.newsapp.repository.RoleRepository;
import com.example.newsapp.repository.UserRepository;
import com.example.newsapp.urils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    final UserRepository userRepository;


    final RoleRepository roleRepository;


    final PasswordEncoder passwordEncoder;


    final RoleService roleService;

    public ApiResponse addUser(UserDTO userDTO) {
        boolean b = userRepository.existsByUsername(userDTO.getUserName());
        if (b) return new ApiResponse("User already exist", false);

        ApiResponse response = roleService.getById(userDTO.getRoleId().longValue());
        if (!response.isSuccess())
            return response;

        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole((Role) response.getObject());
        user.setEnabled(userDTO.getEnabled());

        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse edit(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) return new ApiResponse("User not found", false);
        boolean b = userRepository.existsByUsername(userDTO.getUserName());

        if (b) return new ApiResponse("Username already exist", false);

        ApiResponse response = roleService.getById(userDTO.getRoleId().longValue());
        if (!response.isSuccess())
            return response;

        User user = optionalUser.get();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole((Role) response.getObject());
        user.setEnabled(userDTO.getEnabled());

        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse editMyProfile(RegisterDTO registerDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.existsByUsernameAndIdNot(registerDTO.getUserName(), user.getId()))
            return new ApiResponse("Username already exists!", false);

        if (!registerDTO.getPassword().equals(registerDTO.getPrePassword()))
            return new ApiResponse("Passwords are not compatible!", false);


        user.setFullName(registerDTO.getFullName());
        user.setUsername(registerDTO.getUserName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(user);
        return new ApiResponse("User saved!", true);
    }

    public ApiResponse delete(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("User not found", false);
        userRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }

    public ApiResponse getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> new ApiResponse("User by id!", true, user)).orElseThrow(() -> new RescuersNotFoundEx("user", "id", id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("List User", true, userRepository.findAll());
    }
}
