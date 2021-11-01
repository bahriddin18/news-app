package com.example.newsapp.service;

import com.example.newsapp.entity.User;
import com.example.newsapp.exceptions.RescuersNotFoundEx;
import com.example.newsapp.payload.ApiResponse;
import com.example.newsapp.payload.RegisterDTO;
import com.example.newsapp.repository.RoleRepository;
import com.example.newsapp.repository.UserRepository;
import com.example.newsapp.urils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterDTO registerDTO) {

        boolean b = userRepository.existsByUsername(registerDTO.getUserName());

        if (!registerDTO.getPassword().equals(registerDTO.getPrePassword()))
            return new ApiResponse("Password not equals", false);

        if (b) return new ApiResponse("User already exist", false);

        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setUsername(registerDTO.getUserName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(roleRepository.findByName(AppConstants.USER).orElseThrow(() -> new RescuersNotFoundEx("role", "name", AppConstants.USER)));
        user.setEnabled(true);

        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
