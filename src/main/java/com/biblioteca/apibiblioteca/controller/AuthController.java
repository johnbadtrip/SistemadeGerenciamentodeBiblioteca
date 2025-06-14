package com.biblioteca.apibiblioteca.controller;

import com.biblioteca.apibiblioteca.controller.dto.AuthResponse;
import com.biblioteca.apibiblioteca.controller.dto.LoginRequest;
import com.biblioteca.apibiblioteca.controller.dto.RegisterRequest;
import com.biblioteca.apibiblioteca.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}