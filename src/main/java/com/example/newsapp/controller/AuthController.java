package com.example.newsapp.controller;

import com.example.newsapp.entity.User;
import com.example.newsapp.payload.ApiResponse;
import com.example.newsapp.payload.LoginDTO;
import com.example.newsapp.payload.RegisterDTO;
import com.example.newsapp.security.JwtProvider;
import com.example.newsapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    final AuthService authService;

    final AuthenticationManager authenticationManager;

    final JwtProvider jwtProvider;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        ApiResponse apiResponse = authService.registerUser(registerDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
        User principal = (User) authenticate.getPrincipal();
        String token = jwtProvider.generateToken(principal.getUsername(), principal.getRole());
        return ResponseEntity.ok(token);
    }

}
